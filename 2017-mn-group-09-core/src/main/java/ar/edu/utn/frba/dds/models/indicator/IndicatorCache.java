package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.configuration.EnviromentType;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.utils.ConfigurationUtils;
import ar.edu.utn.frba.dds.utils.DateUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by TATIANA on 30/10/2017.
 */
public class IndicatorCache {

    public static List<IndicatorResult> getResults(String indicatorName, Company company, LocalDate fromDate, LocalDate toDate) {
        ArrayList<IndicatorResult> ret = new ArrayList<>();

        if (EnviromentConfigurationHelper.getEnv() == EnviromentType.APP) {
            try {
                MongoClient mongoClient = new MongoClient(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.mongo"));
                MongoDatabase db = mongoClient.getDatabase(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.mongo.db"));
                MongoCollection<Document> table = db.getCollection(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.indicator.cache"));
                Bson searchQuery = and(eq("indicator", indicatorName),
                        eq("company", company.getName()),
                        (or(eq("date", null),
                                and(gt("date", DateUtils.getStringFromWebDate(fromDate)),
                                        lt("date", DateUtils.getStringFromWebDate(toDate))))));

                for (Document o : table.find(searchQuery)) {
                    ret.add(new IndicatorResult(indicatorName, company,
                            o.get("date") != null ? DateUtils.getDateFromStringWeb(o.get("date").toString()) : null,
                            Double.parseDouble(o.get("result").toString())));
                }

                mongoClient.close();
            } catch (Exception ex) {
                Logger.getLogger(IndicatorCache.class.getName()).error("Mongo Cache no disponible", ex);
            }
        }
        return ret;
    }

    public static void setResult(String indicatorName, Company company, LocalDate date, Double result) {

        if (EnviromentConfigurationHelper.getEnv() == EnviromentType.APP) {
            try {
                MongoClient mongoClient = new MongoClient(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.mongo"));
                MongoDatabase db = mongoClient.getDatabase(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.mongo.db"));
                MongoCollection<Document> table = db.getCollection(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.indicator.cache"));
                Document document = new Document("indicator", indicatorName)
                        .append("company", company.getName())
                        .append("date", date != null ? DateUtils.getStringFromWebDate(date) : null)
                        .append("result", result);

                table.insertOne(document);

                mongoClient.close();
            } catch (Exception ex) {
                Logger.getLogger(IndicatorCache.class.getName()).error("Mongo Cache no disponible", ex);
            }
        }
    }
}
