package cn.leepon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoUtil {
	
	 private static Logger log = Logger.getLogger(MongoUtil.class);
	 
	    private static final MongoUtil instance = new MongoUtil();
	 
	    private static MongoClient mongo = null;
	     
	    private static final String  host = ResourceUtil.getValue("systemConstant", "mongo_host");
	    private static final Integer port = Integer.valueOf(ResourceUtil.getValue("systemConstant", "mongo_port"));
	     
	    /**
	     * 私有化
	     */
	    private MongoUtil() {}
	     
	    /**
	     * 单例
	     * @return
	     */
	    public static MongoUtil getInstance() {
	         
	        return instance;
	         
	    }
	     
	    /**
	     * 初始化MongoDB
	     */
	    public void init() {
	 
	        try {
	 
	            mongo = new MongoClient(host, port);
	            log.info("MongoDB init success!");
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	         
	    }
	 
	    /**
	     * 获取DB对象
	     * @return
	     */
	    @SuppressWarnings("deprecation")
		public DB getDB() {
	         
	        try {
	             
	            if (mongo == null) {
	                 
	                init();
	                log.debug("Get DB : " + ResourceUtil.getValue("systemConstant", "DB_name"));
	                 
	            }
	             
	            return mongo.getDB(ResourceUtil.getValue("systemConstant", "DB_name"));
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	         
	        return null;
	 
	    }
	 
	    /**
	     * 获取集合对象
	     * @param name
	     * @return
	     */
	    private DBCollection getCollection(String name) {
	         
	        try {
	             
	            return getDB().getCollection(name);
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return null;
	 
	    }
	 
	    /**
	     * 插入MongoDB
	     * @param name
	     * @param obj
	     */
	    public void insert(String name, DBObject obj) {
	         
	        try {
	             
	            long begin = System.currentTimeMillis();
	            getCollection(name).insert(obj);
	            long end = System.currentTimeMillis();
	            log.debug("Insert Complete! Cost " + (end - begin) + "/ms");
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	         
	    }
	 
	    /**
	     * 删除指定条件的数据
	     * @param name
	     * @param obj
	     */
	    public void delete(String name, DBObject obj) {
	         
	        try {
	             
	            getCollection(name).remove(obj);
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }
	 
	    /**
	     * 清空集合
	     * @param collection
	     * @throws Exception
	     */
	    public void deleteAll(String collection) {
	 
	        try {
	             
	            List<DBObject> rs = findAll(collection);
	         
	            if (rs != null && !rs.isEmpty()) {
	                 
	                for (int i = 0; i < rs.size(); i++) {
	                    getCollection(collection).remove(rs.get(i));
	                }
	                 
	            }
	         
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	         
	    }
	 
	    /**
	     * 如果更新的数据 不存在 插入一条数据
	     * @param collection
	     * @param setFields
	     * @param whereFields
	     */
	    public void updateOrInsert(String name, DBObject set, DBObject where) {
	 
	        try {
	             
	            getCollection(name).update(where, set, true, false);
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }
	 
	    /**
	     * 只更新存在的数据,不会新增. 批量更新.
	     * @param name
	     * @param setFields
	     * @param whereFields
	     */
	    public void updateExistDataWithBatch(String name, DBObject set, DBObject where) {
	 
	        try {
	             
	            getCollection(name).update(where, new BasicDBObject("$set", set), false, true);
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }
	 
	    /**
	     * 按照ObjectId,批量更新.<br>
	     * @param name
	     * @param ids
	     * @param set
	     */
	    public void updateBatchByObjectId(String name, String ids, DBObject set) {
	         
	        try {
	             
	            if (ids == null || ids == "")
	                return;
	             
	            String[] id = ids.split(",");
	             
	            for (int i = 0; i < id.length; i++) {
	                 
	                BasicDBObject dest = new BasicDBObject();
	                BasicDBObject doc = new BasicDBObject();
	                dest.put("_id", new ObjectId(id[i]));
	                doc.put("$set", set);
	                getCollection(name).update(dest, doc, false, true);
	                 
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	 
	    }
	 
	    /**
	     * 查询全部
	     * @param name
	     * @return
	     */
	    public List<DBObject> findAll(String name) {
	 
	        try {
	             
	            return getCollection(name).find().toArray();
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return null;
	         
	    }
	 
	    /**
	     * 查询1条记录
	     * @param name
	     * @param obj
	     * @return
	     */
	    public DBObject findOne(String name, DBObject obj) {
	 
	        try {
	             
	            DBCollection coll = getCollection(name);
	            return coll.findOne(obj);
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	         
	        return null;
	 
	    }
	 
	    /**
	     * 查询指定条数的记录
	     * @param name
	     * @param obj
	     * @param limit
	     * @return
	     */
	    public List<DBObject> find(String name, DBObject obj, int limit) {
	         
	        try {
	             
	            DBCollection coll = getCollection(name);
	            DBCursor c = coll.find(obj).limit(limit);
	             
	            if (c != null){
	                 
	                List<DBObject> list = new ArrayList<DBObject>();
	                list = c.toArray();
	                 
	                return list;
	                 
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	         
	        return null;
	         
	    }
	 
	    /**
	     * 查询符合的全部数据
	     * @param name
	     * @param where
	     * @return
	     */
	    public List<DBObject> find(String name, DBObject where) {
	         
	        try {
	             
	            DBCursor c = getCollection(name).find(where);
	             
	            if (c != null) {
	                 
	                List<DBObject> list = new ArrayList<DBObject>();
	                list = c.toArray();
	                 
	                return list;
	                 
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return null;
	 
	    }
	     
	    /**
	     * 返回Queue的查询
	     * @param name
	     * @param where
	     * @return
	     * @throws Exception
	     */
	    public LinkedBlockingQueue<DBObject> findQueue(String name, DBObject where) {
	         
	        try {
	             
	            LinkedBlockingQueue<DBObject> queue = new LinkedBlockingQueue<DBObject>();
	             
	            DBCursor c = getCollection(name).find(where);
	             
	            if (c != null) {
	                 
	                for (DBObject obj : c) {
	                    obj = c.next();
	                    queue.offer(obj);
	                }
	                 
	                return queue;
	                 
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return null;
	 
	    }
	 
	    /**
	     * 关闭Mongo链接
	     */
	    public void close() {
	         
	        try {
	             
	            if (mongo != null) {
	                 
	                mongo.close();
	                log.info("MongoClient has benn closed...");
	                 
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }

}
