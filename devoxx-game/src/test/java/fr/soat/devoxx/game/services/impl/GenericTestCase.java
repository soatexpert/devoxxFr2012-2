package fr.soat.devoxx.game.services.impl;

import java.io.File;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContextTest.xml" })
@ActiveProfiles("dev")
public class GenericTestCase extends TestCase {

	private EmbeddedDatabase db;
	
	static IDataSet dataSet;
	static {
		try {
			dataSet = new FlatXmlDataSetBuilder().build(new File("./devoxx-game/src/test/resources/dataset.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Autowired
	DataSource dataSource;
	
	@Before
	public void setUp() throws Exception {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		db = builder.setType(EmbeddedDatabaseType.DERBY).build();
//		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
//		DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
	}
	//
	// @After
	// public void after() throws Exception {
	// IDatabaseConnection dbConn = new
	// DatabaseDataSourceConnection(dataSource);
	// DatabaseOperation.DELETE_ALL.execute(dbConn, dataSet);
	// }

	
	@Test
	public void testDatasource() {
		assertNotNull(dataSource);
	}
	
	@After
	public void closeAll(){
		db.shutdown();
	}
}

