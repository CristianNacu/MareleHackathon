using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Data;
using System.Timers;

namespace ServerFarminator.Database
{
    class DatabaseRequest
    {

        System.Timers.Timer aTimer;



  

        string req = String.Empty;
        public StringBuilder sb = new StringBuilder();
        SqlConnection sqlConnection2 = new SqlConnection("Network Address=localhost; Server=DESKTOP-4HBDUPG\\SQLSRVRCRISTI;Initial Catalog = FarminatorDB;Integrated Security=True;");
        SqlDataReader reader2;
        SqlCommand cmd2 = new SqlCommand();
        SqlConnection sqlConnection1 = new SqlConnection("Network Address=localhost; Server=DESKTOP-4HBDUPG\\SQLSRVRCRISTI;Initial Catalog = FarminatorDB;Integrated Security=True;");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader reader;

        private void OnTimedEvent(object source, ElapsedEventArgs e)
        {
            cmd2.CommandText = "EXEC CheckCrops ";
            cmd2.Connection = sqlConnection2;
            sqlConnection2.Open();
            reader2 = cmd2.ExecuteReader();
            sqlConnection2.Close();
        }

        public DatabaseRequest(string request)
        {
            System.Timers.Timer aTimer = new System.Timers.Timer();
            aTimer.Elapsed += new ElapsedEventHandler(OnTimedEvent);
            aTimer.Interval = 10000;
            aTimer.Enabled = true;
            req = request;
        }

        public string RequestHandler()
        {
                string[] data = req.Split('|');
            if (data.Length < 2)
                return "Fuq";

            if (data[1] == "1")
                return ExistsUser(data[2], data[3]);//user, parola
            else if (data[1] == "2")
                return GetTasksForUser(data[2]);//nume user
            else if (data[1] == "3")
                return GetUnasignedTasks();
            else if (data[1] == "4")
                return UpdateUnasignedTask(data[2], data[3]);//idTask, username
            else if (data[1] == "5")
                return UpdateCompletedTask(data[2]);//idTask, username
            else if (data[1] == "6")
                return Refresh(Int32.Parse(data[2]));//reset task
            else if (data[1] == "7")
                return Undo(Int32.Parse(data[2]));//reset task
            else if (data[1] == "100")
                return GetAllTasks();//get all task
            else if (data[1] == "101")
                return GetTasksForLot(data[2]);//Tasks/lot
            else if (data[1] == "102")
                return GetLotType();//lot type
            else if (data[1] == "103")
                return GetAllLots();//all lots
            else if (data[1] == "104")
                return GetAllUsers();//all users         
            else if (data[1] == "105")
                return GetAllRoles();//all roles
            else if (data[1] == "110")
                return AddUser(data[2], data[3], data[4], data[5], data[6]);
            else if (data[1] == "111")
            {
                if (data.Length == 5)
                    return AddTask(data[2], data[3], data[4]);
                else if (data.Length == 6)
                    return AddTask(data[2], data[3], data[4], data[5]);
            }
            else if (data[1] == "120")
                return UpdateLot(data[2], data[3]);
            else if (data[1] == "130")
                return Stat();
            return "Fuq";
           

        }



        /// GETERS

        private string GetTasksForUser(string username)
        {


            cmd.CommandText = "SELECT T.Id, T.Descriere, P.X, P.Y FROM Task T INNER JOIN Parcele P ON T.ParceaId = P.Id  WHERE MuncitorId = (SELECT Id From Client where Username = '"+ username + "') AND Stare = 1 AND T.ParceaId = P.Id";
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
                Console.WriteLine(sb.ToString());
                //reader.NextResult();
            }
            
            sqlConnection1.Close();

            return sb.ToString();
        }

        private string GetUnasignedTasks()
        {
            cmd.CommandText = "SELECT T.Id, T.Descriere, P.X, P.Y FROM Task T INNER JOIN Parcele P ON T.ParceaId = P.Id  WHERE MuncitorId  is NULL AND Stare = 1";
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
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string GetAllTasks()
        {
            cmd.CommandText = "Select Id, Descriere, DataStart, MuncitorId, Stare, ParceaId From Task Where Stare = 1";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");
                }
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string GetAllUsers()
        {
            cmd.CommandText = "Select Id, Nume, Prenume, IdRol From Client";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");
                }
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string GetTasksForLot(string nrparcea)
        {


            cmd.CommandText = "Select Id, Descriere, DataStart, MuncitorId, Stare, ParceaId From Task Where ParceaId = " + nrparcea+"  AND Stare = 1";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");

                }
                Console.WriteLine(sb.ToString());
                //reader.NextResult();
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string GetLotType()
        {


            cmd.CommandText = "Select Nume From ParceleTypes";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");

                }
                Console.WriteLine(sb.ToString());
                //reader.NextResult();
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string GetAllLots()
        {
            cmd.CommandText = "Select * From Parcele";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");
                }
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        private string GetAllRoles()
        {
            cmd.CommandText = "Select * From Rol";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");
                }
            }

            sqlConnection1.Close();
            return sb.ToString();
        }

        /// EXISTENCE
        private string ExistsUser(string username, string passwd)
        {

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

        /// UPDATE

        private string UpdateUnasignedTask(string idTask, string username)
        {
            cmd.CommandText = "Update Task Set MuncitorId = (SELECT Id FROM Client Where Username = '"+username+"') Where Id= '"+idTask+"'";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();

            sqlConnection1.Close();
            return "Gata";

        }

        private string UpdateCompletedTask(string idTask)
        {
            cmd.CommandText = "Update Task Set Stare=0, DataFinal=GETDATE()  Where Id= " + idTask;
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();

            sqlConnection1.Close();
            return "MadeIt";

        }

        private string UpdateLot(string idLot, string type)
        {
            cmd.CommandText = "Update Parcele Set Type='"+type+"' Where Id= " + idLot;
            cmd.Connection = sqlConnection1;
            sqlConnection1.Open();
            reader = cmd.ExecuteReader();
            sqlConnection1.Close();


            cmd.CommandText = "EXEC TimeCrop "+idLot+", '"+type+"'";
            cmd.Connection = sqlConnection1;
            sqlConnection1.Open();
            reader = cmd.ExecuteReader();
            sqlConnection1.Close();


            return "MadeIt";

        }


        /// REFRESH

        private string Refresh(int whatToRefresh)
        {
            cmd.CommandText = "EXEC refresh "+whatToRefresh;
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            sqlConnection1.Close();
            return "OkiBoss";
        }

        private string Undo(int undoid)
        {
            cmd.CommandText = "EXEC undo " + undoid;
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();
            sqlConnection1.Close();
            return "OkiBoss";
        }

        /// ADD

        private string AddUser(string Nume, string Prenume, string Username, string Password, string IdRol)
        {
            cmd.CommandText = "INSERT INTO Client VALUES('" + Nume + "','" + Prenume + "','" + Username + "','" + Password +"',"+ IdRol + ")";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();

            sqlConnection1.Close();
            return "MadeIt";

        }

        private string AddTask(string Descriere, string DataStart, string ParceaId)
        {
            cmd.CommandText = "INSERT INTO Task VALUES('" + Descriere + "'," + DataStart + ", NULL, NULL, 1, " +  ParceaId + ")";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();

            sqlConnection1.Close();
            return "MadeIt";

        }

        private string AddTask(string Descriere, string DataStart, string ParceaId, string MuncitorId)
        {
            cmd.CommandText = "INSERT INTO Task VALUES('" + Descriere + "'," + DataStart + ", NULL, "+MuncitorId+", 1, " + ParceaId + ")";
            cmd.Connection = sqlConnection1;

            sqlConnection1.Open();

            reader = cmd.ExecuteReader();

            sqlConnection1.Close();
            return "MadeIt";

        }

/// stats

        private string Stat()
        {
            cmd.CommandText = "SELECT t.MuncitorId,COUNT(*) FROM Task T Where T.Stare = 0 GROUP BY t.MuncitorId";
            cmd.Connection = sqlConnection1;
            sqlConnection1.Open();
            reader = cmd.ExecuteReader();

            while (reader.Read())
            {

                for (int i = 0; i < reader.VisibleFieldCount; i++)
                {
                    string column = reader[i].ToString();
                    sb.Append(column + ";");
                }
            }
            sqlConnection1.Close();
            return sb.ToString();

        }
        


    }
}
