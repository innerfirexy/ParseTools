/**
 * Created by yvx5085 on 2/15/16.
 */

package parsetools

import java.sql.*
import edu.standford.nlp.simple.*


// the fun that returns a db connection
fun getConn(db: String, local: Boolean = true) : Connection {
    Class.forName("com.mysql.jdbc.Driver")
    val port = if (local) 3306 else 1234
    val url = "jdbc:mysql://localhost:" + port.toString() + "/" + db + "?user=yang&password=05012014"
    val conn = DriverManager.getConnection(url)
    return conn
}

// define the data class to hold the data from query
data class result(val xmlID: String, val divIndex: Int, val globalID: Int, val rawWord: String)

// the fun that runs a sql query, and then stores the results to an Array of result data
fun getResults(conn: Connection, query: String) : List<result> {
    val stmt = conn.createStatement()
    val rs = stmt.executeQuery(query)

    var results: MutableList<result> = arrayListOf()
    while (rs.next()) {
        val xmlID = rs.getString("xmlID")
        val divIndex = rs.getInt("divIndex")
        val globalID = rs.getInt("globalID")
        val rawWord = rs.getString("rawWord")
        results.add(result(xmlID, divIndex, globalID, rawWord))
    }
    return results
}


// main
fun main(args: Array<String>) {
    val conn = getConn("bnc", local = false)

    val query = "SELECT xmlID, divIndex, globalID, rawWord FROM entropy_DEM100 WHERE rawWord IS NOT NULL AND rawWord <> \'\'"
    val results = getResults(conn, query)
    println(results.size)
}

