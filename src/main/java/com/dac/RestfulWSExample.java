package com.dac;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.codehaus.jackson.map.ObjectMapper;

import com.dac.model.Catalog;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;

@Path("/catalogs")
public class RestfulWSExample {
	static final String api_version = "1.01A rev.18729";
	static Logger logger = Logger.getLogger(RestfulWSExample.class);
	static String xmlString = null;

	static {
		System.out.println("Initializing Internal DataStore...");
		// Do connection work here
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version: " + api_version + "</p>";
	}

	// This is the default @PATH
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Catalog> getAllCatalog() {

		MongoDatabase database = getMongoDatabase();

		System.out.println("Getting all catalogs...");
		ArrayList<Catalog> catalogList = retrieveAllDocuments(database);
		return catalogList;
	}

	private ArrayList<Catalog> retrieveAllDocuments(MongoDatabase database) {

		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("catCollection");
		System.out.println("Collection sampleCollection selected successfully");
		ArrayList<Catalog> catalog = findAllCatalog(collection);
		return catalog;
	}

	public ArrayList<Catalog> findAllCatalog(MongoCollection<Document> collection) {
		FindIterable<Document> result = collection.find();
		System.out.println("Total Collection: " + collection.count());
		ArrayList<Catalog> catalogss = new ArrayList<Catalog>();
		MongoCursor<Document> cursor = result.iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				catalogss.add(formCatalog(doc));

			}
		} finally {
			cursor.close();
		}

		return catalogss;
	}

	private Catalog formCatalog(Document document) {
		Catalog catalog = new Catalog();
		if (document.getInteger("id") != null)
			catalog.setCatid(document.getInteger("id"));
		else
			catalog.setCatid(document.getInteger("catid"));
		catalog.setName(document.getString("name"));
		catalog.setRegion(document.getString("region"));
		catalog.setAssetname(document.getString("assetname"));
		catalog.setAssetdesc(document.getString("assetdesc"));
		catalog.setCid(document.getString("cid"));
		catalog.setCname(document.getString("cname"));
		catalog.setSid(document.getString("sid"));
		catalog.setSname(document.getString("sname"));
		catalog.setModel(document.getString("model"));
		catalog.setTags(document.getString("tags"));
		catalog.setIsrequired(document.getBoolean("isrequired"));
		return catalog;
	}

	private MongoDatabase getMongoDatabase() {
		// Creating a Mongo client
		MongoClient mongo = new MongoClient("34.212.147.102", 27017);

		// Creating Credentials
		MongoCredential credential;
		credential = MongoCredential.createCredential("edwinj", "mongoDB", "edwinj".toCharArray());
		System.out.println("Connected to the database successfully");

		// Accessing the database
		MongoDatabase database = mongo.getDatabase("mongoDB");
		System.out.println("Credentials ::" + credential);

		return database;

	}

	@Path("{catid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Catalog getCatalogById(@PathParam("catid") String id) {
		System.out.println("Getting catalog by ID: " + id);
		Catalog catalog = new Catalog();
		MongoCollection<Document> collection = getMongoDatabase().getCollection("catCollection");
		System.out.println(collection.count());
		BasicDBObject query = new BasicDBObject();
		if(!id.equalsIgnoreCase("search"))
			query.append("catid", Integer.parseInt(id));
		FindIterable<Document> doc = collection.find(query);
		for (Document document : doc) {
			
			catalog.setCatid(document.getInteger("catid"));
			catalog.setName(document.getString("name"));
			catalog.setRegion(document.getString("region"));
			catalog.setAssetname(document.getString("assetname"));
			catalog.setAssetdesc(document.getString("assetdesc"));
			catalog.setCid(document.getString("cid"));
			catalog.setCname(document.getString("cname"));
			catalog.setSid(document.getString("sid"));
			catalog.setSname(document.getString("sname"));
			catalog.setModel(document.getString("model"));
			catalog.setTags(document.getString("tags"));
			catalog.setIsrequired(document.getBoolean("isrequired"));
		}

		if (catalog != null) {
			logger.info("Inside getCatalogById, returned: ");
		} else {
			logger.info("Inside getCatalogById, ID: " + id + ", NOT FOUND!");
		}
		return catalog;
	}

	@Path("{catid}")
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Catalog updateCatalog(Catalog catalog) {
		if (catalog != null) {
			// Retrieving a collection
			MongoCollection<Document> collection = getMongoDatabase().getCollection("catCollection");
			System.out.println("Collection catCollection selected successfully");
			Document doc = findAndUpdateCollection(collection, catalog);

			System.out.println("updateCatalog with ID: " + catalog.getCatid());
			if (doc != null) {
				logger.info("Inside updateCatalog, returned: " + catalog.toString());
			} else {
				logger.info("Inside updateCatalog, ID: " + catalog.getCatid() + ", NOT FOUND!");
			}
		}
		return catalog;
	}

	public static Document findAndUpdateCollection(final MongoCollection<Document> collection, final Catalog catalog) {
		ObjectMapper mapper = new ObjectMapper();
		Document resultDocument = null;
		String jsonString;
		try {
			// findOneAndUpdate using JSON into MongoDB
			jsonString = mapper.writeValueAsString(catalog);
			System.out.println(String.format("Item #%s: %s", catalog.getCatid(), jsonString));
			BasicDBObject query = new BasicDBObject();
			query.append("catid", catalog.getCatid());
			BasicDBObject doc = BasicDBObject.parse(jsonString);
			Bson newDocument = new Document("$set", doc);
			resultDocument = collection.findOneAndUpdate(query, newDocument,
					(new FindOneAndUpdateOptions()).upsert(true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultDocument;

	}

	@Path("/search/{query}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Catalog> searchByName(@PathParam("query") String query) {
		System.out.println("Searching catalog by Name: " + query);

		ArrayList<Catalog> catalogList = new ArrayList<Catalog>();
		ArrayList<Catalog> catArry = getAllCatalog();
		for (Catalog c : catArry) { 
			if (query != null  && c.getName().toUpperCase().contains(query.toUpperCase()))
				catalogList.add(c);
		}
		if(query == null || query.isEmpty() || query.equals("undefined"))
			return catArry;
		return catalogList;
	}

	@Path("/add")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Catalog addCatalog(Catalog catalog) {
		System.out.println("Adding catalog with ID: " + catalog.getCatid());

		if (catalog != null) {

			// Retrieving a collection
			MongoCollection<Document> collection = getMongoDatabase().getCollection("catCollection");
			System.out.println("Collection catCollection selected successfully");

			Document document = new Document().append("catid", catalog.getCatid()).append("name", catalog.getName())
					.append("region", catalog.getRegion()).append("assetname", catalog.getAssetname())
					.append("assetdesc", catalog.getAssetdesc()).append("cid", catalog.getCid())
					.append("cname", catalog.getCname()).append("sid", catalog.getSid())
					.append("sname", catalog.getSname()).append("model", catalog.getModel())
					.append("tags", catalog.getTags()).append("isrequired", catalog.isIsrequired());

			collection.insertOne(document);

		} else {
			System.out.println("Inside addCatalog, Unable to add catalogs...");
		}
		return catalog;
	}

	@Path("{catid}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Catalog deleteCatalogById(@PathParam("catid") String catid) {
		System.out.println("Deleting catalog with ID: " + catid);
		Catalog catalog = new Catalog();
		// findOneAndDelete from MongoDB
		System.out.println("Using findOneAndDeleteCollection to delete ID: " + catid);
		MongoCollection<Document> collection = getMongoDatabase().getCollection("catCollection");
		Document document = collection.findOneAndDelete(Filters.eq("catid", Integer.parseInt(catid)));
		if(document != null) {
			catalog.setCatid(document.getInteger("catid"));
			catalog.setName(document.getString("name"));
			catalog.setRegion(document.getString("region"));
			catalog.setAssetname(document.getString("assetname"));
			catalog.setAssetdesc(document.getString("assetdesc"));
			catalog.setCid(document.getString("cid"));
			catalog.setCname(document.getString("cname"));
			catalog.setSid(document.getString("sid"));
			catalog.setSname(document.getString("sname"));
			catalog.setModel(document.getString("model"));
			catalog.setTags(document.getString("tags"));
			catalog.setIsrequired(document.getBoolean("isrequired"));
		}
		if (document != null) {
			logger.info("Inside deleteCatalogById, returned: " + document);
		} else {
			logger.info("Inside deleteCatalogById, ID: " + catid + ", NOT FOUND!");
		}
		return catalog;
	}
}
