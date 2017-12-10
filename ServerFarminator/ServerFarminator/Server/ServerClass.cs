using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using ServerFarminator.Database;

namespace ServerFarminator.Server
{
    public class StateObject
    {
        public Socket workSocket = null;

        public const int MAXLEN = 1024;

        public byte[] buffer = new byte[MAXLEN];

        public StringBuilder sb = new StringBuilder();
    }

    class Server
    {
        public static ManualResetEvent allDone = new ManualResetEvent(false);

        public static void StartListening()
        {
            IPHostEntry ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            IPAddress ipAddress = ipHostInfo.AddressList[1];
            Console.WriteLine(ipAddress.ToString());
            IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 4300);
            Socket listener = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(100);
                while (true)
                {
                    allDone.Reset();
                    listener.BeginAccept(new AsyncCallback(AcceptCallback), listener);
                    allDone.WaitOne();
                }

            }catch(Exception e)
            {
                Console.WriteLine(e.ToString()+" FUCK");
            }
        }

        public static void AcceptCallback(IAsyncResult rez)
        {
            allDone.Set();

            Console.WriteLine("Bingo");
            Socket listener = (Socket)rez.AsyncState;
            Socket handler = listener.EndAccept(rez);
            StateObject state = new StateObject();
            state.workSocket = handler;
            handler.BeginReceive(state.buffer, 0, StateObject.MAXLEN,0, new AsyncCallback(ReadCallback), state);

        }

        public static void ReadCallback(IAsyncResult rez)
        {
            String content = String.Empty;
            StateObject state = (StateObject)rez.AsyncState;
            Socket handler = state.workSocket;

            int bytesRead = handler.EndReceive(rez);

            if (bytesRead > 0 && bytesRead < StateObject.MAXLEN)
            {
                state.sb.Append(Encoding.ASCII.GetString(
                    state.buffer, 0, bytesRead));
                content = state.sb.ToString();
                DatabaseRequest db = new DatabaseRequest(content);
                Send(handler, db.RequestHandler());
            }
            if(bytesRead == StateObject.MAXLEN)
            {
                state.sb.Append(Encoding.ASCII.GetString(
                    state.buffer, 0, bytesRead));
                handler.BeginReceive(state.buffer, 0, StateObject.MAXLEN, 0,
                new AsyncCallback(ReadCallback), state);
              
            }

        }

        private static void Send(Socket handler, String data)
        {
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            handler.BeginSend(byteData, 0, byteData.Length, 0,
                new AsyncCallback(SendCallback), handler);
        }

        private static void SendCallback(IAsyncResult ar)
        {
            try
            { 
                Socket handler = (Socket)ar.AsyncState;

                int bytesSent = handler.EndSend(ar);
                Console.WriteLine("Sent {0} bytes to client.", bytesSent);

                handler.Shutdown(SocketShutdown.Both);
                handler.Close();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

    }
}
