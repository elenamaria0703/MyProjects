using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab4_SGBD
{
    class Program
    {
        public static int attempts=10;//numarul de repetari
        public static string conString = "Data Source=DESKTOP-P13AP75\\SQLEXPRESS; Initial Catalog=HavEATreat; Integrated Security=True";
        static void Main(string[] args)
        {
            Thread trans1 = new Thread(runTrans1);//threadul pentru tranzactia 1
            Thread trans2 = new Thread(runTrans2);//threadul pentru tranzactia 2
            trans1.Start();
            trans2.Start();
        }

        public static void runTrans1()
        {
            if (attempts > 0)
            {
                try
                {
                    using (SqlConnection connection = new SqlConnection(conString))
                    {
                        SqlCommand cmd = new SqlCommand("spTrans1", connection);
                        cmd.CommandType = CommandType.StoredProcedure;
                        connection.Open();
                        cmd.ExecuteNonQuery();//daca se executa query-ul inseamna ca nu a aparut deadlock
                        //putem seta attempts la 0, adica am obtinut un rezultat cu succes
                        //altfel in clauza catch scadem nr de incerrcari si afisam faptul ca a aparut un deadlock
                    }
                }
                catch (SqlException e)
                {
                    if (e.Number == 1205)
                    {
                        Console.Write("Deadlock Occured!");
                        attempts--;
                    }
                    else
                    { 
                        throw;
                    }
                        
                }
                attempts = 0;
            }  
        }
        private static void runTrans2()
        {
            if (attempts > 0)
            {
                try
                {
                    using (SqlConnection connection = new SqlConnection(conString))
                    {
                        SqlCommand cmd = new SqlCommand("spTrans2", connection);
                        cmd.CommandType = CommandType.StoredProcedure;
                        connection.Open();
                        cmd.ExecuteNonQuery();
                    }
                }
                catch (SqlException e)
                {
                    if (e.Number == 1205)
                    {
                        Console.Write("Deadlock Occured!");
                        attempts--;
                    }
                    else
                    {
                        throw;
                    }

                }
                attempts = 0;
            }
        }  
    }
}
