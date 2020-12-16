package c.a.util.mongodb;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Test;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

import all.gen.sys_file.t.entity.SysFileT;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
/**
 *
 */
public class MongoDBImgUtil extends MongoDBCoreUtil {
	// 上传文件
	@Test
	public SysFileT upload(File file, String customFileName) throws Exception {
		SysFileT sysFileT = new SysFileT();
		String domainName = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("mongodb.local.port").toString(), "mongodb.local.domain");
		MongoDBUtil mongoDBUtil = MongoDBUtil.findInstance();
		MongoDatabase mongoDatabase = mongoDBUtil.findMongoDatabase();
		InputStream inputStream = new FileInputStream(file);
		// 创建一个容器，传入一个`MongoDatabase`类实例db
		GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase);
		// 上传
		// http://129.204.12.101/girdfstest/9078207b-57bb-4e4b-8b94-c373c96b0fc5.jpg
		String fileName = file.getName();

		System.out.println("fileName=" + fileName);
		if (StringUtil.isNotBlank(customFileName)) {
			fileName = customFileName;
		}
		ObjectId objectId = gridFSBucket.uploadFromStream(fileName, inputStream);
		// 上传完成。 文件ID=5d2f06260b27dd3cacff8903
		// http://129.204.12.101/girdfsjava/5d2f06260b27dd3cacff8903
		System.out.println("上传完成,objectId=" + objectId);
		sysFileT.setFileCode(objectId.toString());
		sysFileT.setFileUrl(domainName + fileName);
		sysFileT.setFileName(fileName);
		return sysFileT;
	}
}
