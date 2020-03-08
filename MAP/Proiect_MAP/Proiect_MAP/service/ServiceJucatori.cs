using Proiect_MAP.model;
using Proiect_MAP.repository;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.service
{
    class ServiceJucatori
    {
        IRepository<int, Jucator> repoJucatori;
        IRepository<int, Echipa> repoEchipe;
        IRepository<int, Elev> repoElevi;
        IDictionary<int, string> scoli=new Dictionary<int,string>();
        string filename;

        public ServiceJucatori(string filename,IRepository<int,Jucator> repoJuc,IRepository<int,Echipa> repoEch,IRepository<int,Elev> repoE)
        {
            repoJucatori = repoJuc;
            repoEchipe = repoEch;
            repoElevi = repoE;
            this.filename = filename;
            getScoli();
            populateJucatori();
        }
        private void populateJucatori()
        {
            repoElevi.FindAll().ToList().ForEach(e =>
            {
                Echipa echipa = getEchipa(e.Scoala);
                Jucator jucator = new Jucator
                {
                    ID = e.ID,
                    Nume = e.Nume,
                    Scoala = e.Scoala,
                    Echipa = echipa
                };
                repoJucatori.Save(jucator);
            });
        }
        private Echipa getEchipa(string scoala)
        {
            foreach (KeyValuePair<int, string> entry in scoli)
            {
                if (entry.Value.Equals(scoala))
                    return repoEchipe.FindOne(entry.Key);
            }
            return null;
        }
        private void getScoli()
        {
            using (StreamReader reader = new StreamReader(filename))
            {
                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');
                    scoli[int.Parse(fields[0])]= fields[1];
                }
            }
        }
        public List<Jucator> FindAllJucatori()
        {
            return repoJucatori.FindAll().ToList();
        }
    }
}
