using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Data;

namespace ServerFarminator.Database
{
    class DatabaseRequest
    {
        string req = String.Empty;
        public StringBuilder sb = new StringBuilder();

        public DatabaseRequest(string request)
        {
            req = request;
        }

        public string RequestHandler()
        {
            string[] data = req.Split('|');
            if (data[1] == "1")
                return ExistsUser(data[2], data[3]);//user, parola
            else if (data[1] == "2")
                return GetTasksForUser(data[2]);//nume user
            else if (data[1] == "3")
                return GetUnasignedTasks();
            else if (data[1] == "4")
                UpdateUnasignedTask(data[2], data[3]);//idTask, username
            return "Fuq";
        }

        private string GetTasksForUser(string username)
        {
            SqlConnection sqlConnection1 = new SqlConnection("Network Address=localhost; Server=DESKTOP-4HBDUPG\\SQLSRVRCRISTI;Initial Catalog = FarminatorDB;Integrated Security=True;");
            SqlCommand cmd = new SqlCommand();
            SqlDataReader reader;

            cmd.CommandText = "Select Id, Descriere From Task Where MuncitorId = (SELECT Id From Client where Username = '"+ username + "')";
            cmd.Connection = sqlConnection1;
            
            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column+";");
                    
                }
                reader.NextResult();
            }
            
            sqlConnection1.Close();
            return sb.ToString();
        }


        private string GetUnasignedTasks()
        {
            SqlConnection sqlConnection1 = new SqlConnection("Network Address=localhost; Server=DESKTOP-4HBDUPG\\SQLSRVRCRISTI;Initial Catalog = FarminatorDB;Integrated Security=True;");
            SqlCommand cmd = new SqlCommand();
            SqlDataReader reader;

            cmd.CommandText = "Select Id, Descriere From Task Where MuncitorId is NULL";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column+";");

                }
                reader.NextResult();
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string ExistsUser(string username, string passwd)
        {
            SqlConnection sqlConnection1 = new SqlConnection("Network Address=localhost; Server=DESKTOP-4HBDUPG\\SQLSRVRCRISTI;Initial Catalog = FarminatorDB;Integrated Security=True;");
            SqlCommand cmd = new SqlCommand();
            SqlDataReader reader;

            cmd.CommandText = "Select * From Client Where Username = '" + username+ "' AND Password = '" + passwd+"'";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            
            if (reader.Read())
            {
                sqlConnection1.Close();
                return "true";
            }
            sqlConnection1.Close();
            return "false";
        }

        private void UpdateUnasignedTask(string idTask, string username)
        {
            SqlConnection sqlConnection1 = new SqlConnection("Network Address=localhost; Server=DESKTOP-4HBDUPG\\SQLSRVRCRISTI;Initial Catalog = FarminatorDB;Integrated Security=True;");
            SqlCommand cmd = new SqlCommand();
            SqlDataReader reader;

            cmd.CommandText = "Update Task Set MuncitorId = (SELECT Id FROM Client Where Username = '"+username+"') Where Id= '"+idTask+"'";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
        }


    }
}
