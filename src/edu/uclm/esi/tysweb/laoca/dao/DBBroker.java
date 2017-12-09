package edu.uclm.esi.tysweb.laoca.dao;

import java.sql.Connection;

public class DBBroker {
	private Pool pool;

	private DBBroker() {
		this.pool = new Pool(2);
	}

	private static class BrokerHolder {
		static DBBroker singleton = new DBBroker();
	}

	public static DBBroker get() {
		return BrokerHolder.singleton;
	}

	public Connection getBD() throws Exception {
		return this.pool.getBD();
	}

	public void close(Connection bd) {
		this.pool.close(bd);
	}

}
