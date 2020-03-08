using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class DataReader
    {
        public static List<T> ReadData<T>(string filename, CreateEntity<T> createEntity)
        {
            if (createEntity != null)
            {
                List < T > list = new List<T>();
                using (StreamReader reader = new StreamReader(filename))
                {
                    string line;
                    while ((line = reader.ReadLine()) != null)
                    {
                        T t = createEntity(line);
                        list.Add(t);
                    }
                };
                return list;
            }
            return null;
        }
    }
}
