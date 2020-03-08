using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class EntityToFileMapping
    {
        public static Elev CreateElev(string line)
        {
            string[] fields = line.Split(',');
            Elev elev = new Elev
            {
                ID = int.Parse(fields[0]),
                Nume = fields[1],
                Scoala=fields[2]
            };
            return elev;
        }
        public static Echipa CreateEchipa(string line)
        {
            string[] fields = line.Split(',');
            Echipa echipa = new Echipa
            {
                ID = int.Parse(fields[0]),
                Nume = fields[1]
            };
            return echipa;
        }
        public static Meci CreateMeci(string line)
        {
            string filename= "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\echipe.txt";
            List<Echipa> echipe = DataReader.ReadData(filename, EntityToFileMapping.CreateEchipa);
            string[] fields = line.Split(',');
            Echipa echipa1 = echipe[int.Parse(fields[1])-1];
            Echipa echipa2 = echipe[int.Parse(fields[2])-1];
            DateTime data = DateTime.ParseExact(fields[3], "yyyy-MM-dd", CultureInfo.InvariantCulture);
            Meci meci = new Meci
            {
                ID=int.Parse(fields[0]),
                Echipa1 = echipa1,
                Echipa2 = echipa2,
                Data = data
            };
            return meci;
        }
        public static JucatorActiv CreateJucatorActiv(string line)
        {
            string[] fields = line.Split(',');
            JucatorActiv jucator = new JucatorActiv
            {
                ID=int.Parse(fields[0]),
                IdJucator = int.Parse(fields[1]),
                IDMeci = int.Parse(fields[2]),
                NrPctInscrise = int.Parse(fields[3]),
                Tip = (Tip)Enum.Parse(typeof(Tip), fields[4])
            };
            return jucator;
        }
    }
}
