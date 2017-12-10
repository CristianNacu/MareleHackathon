using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ServerFarminator.Server;
using ServerFarminator.Database;
namespace ServerFarminator
{
    class Program
    {
        static void Main(string[] args)
        {
            Server.Server.StartListening();

        }
    }
}
