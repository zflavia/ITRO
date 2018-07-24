environments {
	dev {
		mediu='dev'
		database {
			url='jdbc:mysql://localhost:3306/itro'
			username='root'
			password='root'
			driver='com.mysql.jdbc.Driver'
		}
		wildfly {
		    serverRootDir='/Users/flaviamicota/Documents/work/proiecte/itro-istoriaTraduceri-Litere/wildfly-11.0.0.CR1'
		}
	}
	prod {
		mediu='test'
		database {
			url='jdbc:mysql://localhost:3306/itro'
			username='root'
			password='root'
			driver='com.mysql.jdbc.Driver'
		}
		
	}
}