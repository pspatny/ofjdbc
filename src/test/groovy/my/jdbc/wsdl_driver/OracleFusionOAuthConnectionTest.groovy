package my.jdbc.wsdl_driver

import org.junit.jupiter.api.Test

import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.ResultSet

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

class OracleFusionOAuthConnectionTest {

    @Test
    void connectUsingSystemEnvOAuthProvider() {
        String url = "jdbc:wsdl://hctz-dev9.fa.ocs.oraclecloud.com/xmlpserver/services/ExternalReportWSSService?WSDL:/Custom/Financials/RP_ARB.xdo"

        Properties props = new Properties()
        props.setProperty("oauthProviderClass", "my.jdbc.wsdl_driver.SystemEnvOAuthProvider")

// set ENV properties for the test in test configuration - do not put your credentials in the code
//     OFJDBC_OAUTH_CLIENT_ID= <your-client-id> ;OFJDBC_OAUTH_CLIENT_SECRET= <your-client-secret>

        System.setProperty("ofjdbc.oauth.token.endpoint", "https://idcs-b34c11057af348048bf55780869e47ae.identity.oraclecloud.com/oauth2/v1/token")
        System.setProperty("ofjdbc.oauth.scope", "https://hctz-dev9.fa.ocs.oraclecloud.com/")

        Driver driver = new WsdlDriver()
        DriverManager.registerDriver(driver)

        Connection conn = DriverManager.getConnection(url, props)
        assertNotNull(conn)
        ResultSet rs = conn.createStatement().executeQuery("SELECT 1 from dual")
        assertNotNull(rs)
        assertEquals(true, rs.next())
        assertEquals(1, rs.getInt(1))
        rs.close()
        conn.close()
    }

}
