using Proiect_MAP.model;
using Proiect_MAP.repository;
using Proiect_MAP.service;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP
{
    class Program
    {
        static void Main(string[] args)
        {
            List<Elev> elevi = GetServiceElevi().FindAllElevi();
            List<Echipa> echipe = GetServiceEchipe().FindAllEchipe();
            List<Jucator> jucatori = GetServiceJucatori().FindAllJucatori();
            List<Meci> meciuri = GetServiceMeciuri().FindAllMeciuri();
            List<JucatorActiv> jucatoriActivi = GetServiceJucatoriActivi().FindAllJucatoriActivi();

            JucatoriEchipa("Houston Rockets").ForEach(j => Console.WriteLine(j.ToString()));
            Console.WriteLine();
            JucatoriiActiviEchipa("Houston Rockets", 1).ForEach(j => Console.WriteLine(j.ToString()));
            DateTime dataStart = DateTime.ParseExact("2020-02-01", "yyyy-MM-dd", CultureInfo.InvariantCulture);
            DateTime dataEnd = DateTime.ParseExact("2020-04-01", "yyyy-MM-dd", CultureInfo.InvariantCulture);
            MeciuriPerioada(dataStart, dataEnd);
            Console.WriteLine();
            ScorMeci(1);
            // echipe.ForEach(e => Console.WriteLine(e.ToString()));
            //elevi.ForEach(e => Console.WriteLine(e.ToString()));
            //jucatori.ForEach(j => Console.WriteLine(j.ToString()));
            // meciuri.ForEach(m => Console.WriteLine(m.ToString()));
            //jucatoriActivi.ForEach(j => Console.WriteLine(j.ToString()));


            
        }

        private static ServiceElevi GetServiceElevi()
        {
            string filename = "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\elevi.txt";
            IRepository<int, Elev> repo = new RepositoryElevi(filename);
            ServiceElevi service = new ServiceElevi(repo);
            return service;
        }
        private static ServiceEchipe GetServiceEchipe()
        {
            string filename= "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\echipe.txt";
            IRepository<int, Echipa> repo = new RepositoryEchipe(filename);
            ServiceEchipe service = new ServiceEchipe(repo);
            return service;
        }
        private static ServiceJucatori GetServiceJucatori()
        {
            string filenameEch = "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\echipe.txt";
            string filenameEl = "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\elevi.txt";
            string filename = "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\scoli.txt";
            IRepository<int, Elev> repoEl = new RepositoryElevi(filenameEl);
            IRepository<int, Echipa> repoEch = new RepositoryEchipe(filenameEch);
            IRepository<int, Jucator> repoJuc = new RepositoryJucatori();
            ServiceJucatori service = new ServiceJucatori(filename, repoJuc, repoEch, repoEl);
            return service;
        }
        private static ServiceMeciuri GetServiceMeciuri()
        {
            string filename= "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\meciuri.txt";
            IRepository<int, Meci> repo = new RepositoryMeciuri(filename, EntityToFileMapping.CreateMeci);
            ServiceMeciuri service = new ServiceMeciuri(repo);
            return service;
        }
        private static ServiceJucatoriActivi GetServiceJucatoriActivi()
        {
            string filename = "C:\\Users\\Maria\\source\\repos\\Proiect_MAP\\Proiect_MAP\\data\\jucatoriActivi.txt";
            IRepository<int,JucatorActiv > repo= new RepositoryJucatoriActivi(filename, EntityToFileMapping.CreateJucatorActiv);
            ServiceJucatoriActivi service = new ServiceJucatoriActivi(repo);
            return service;
        }

        private static List<Jucator> JucatoriEchipa(string echipa)
        {
            List<Jucator> jucatori = GetServiceJucatori().FindAllJucatori();
            var rez = from j in jucatori
                      where j.Echipa.Nume.Equals(echipa)
                      select j;
            return rez.ToList();
        }
        private static List<JucatorActiv> JucatoriiActiviEchipa(string echipa,int meci)
        {
            List<JucatorActiv> jucatoriActivi = GetServiceJucatoriActivi().FindAllJucatoriActivi();
            var res = (from ja in jucatoriActivi
                       where ja.IDMeci.Equals(meci)
                       select ja).ToList();
            List<JucatorActiv> jucatori = new List<JucatorActiv>();
            JucatoriEchipa(echipa).ForEach(j =>
            {
                res.ForEach(ja =>
                {
                    if (ja.IdJucator.Equals(j.ID))
                        jucatori.Add(ja);
                });
            });
            return jucatori;
        }
        private static void MeciuriPerioada(DateTime dateStart,DateTime dateEnd)
        {
            List<Meci> meciuri = GetServiceMeciuri().FindAllMeciuri();

            var rez = (from m in meciuri
                       where DateTime.Compare(dateStart, m.Data) < 0 && DateTime.Compare(m.Data, dateEnd) < 0
                       select m).ToList();
            rez.ForEach(m => Console.WriteLine(m.ToString()));
        }
        private static void ScorMeci(int meci)
        {
            List<JucatorActiv> jucatoriActivi = GetServiceJucatoriActivi().FindAllJucatoriActivi();
            List<Meci> meciuri = GetServiceMeciuri().FindAllMeciuri();
            List<Echipa> echipe = GetServiceEchipe().FindAllEchipe();
            var meciul = (from m in meciuri where m.ID.Equals(meci) select m).ToList()[0];
            var jucatoriEchipa1 = JucatoriiActiviEchipa(meciul.Echipa1.Nume, meci);
            var jucatoriEchipa2 = JucatoriiActiviEchipa(meciul.Echipa2.Nume, meci);
            var scor1 = (from j in jucatoriEchipa1
                        select new { Echipa1 = meciul.Echipa1.Nume, Scor = jucatoriEchipa1.Sum(x => x.NrPctInscrise) }).ToList();
            var scor2 = (from j in jucatoriEchipa2
                         select new { Echipa2 = meciul.Echipa2.Nume, Scor = jucatoriEchipa2.Sum(x => x.NrPctInscrise) }).ToList();
            Console.WriteLine(scor1[0]);
            Console.WriteLine(scor2[0]);
        }
    }
}
