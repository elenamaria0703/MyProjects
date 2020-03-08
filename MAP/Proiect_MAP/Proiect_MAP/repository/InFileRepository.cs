using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    public delegate E CreateEntity<E>(string line);
    
    class InFileRepository<ID,E>:InMemoryRepository<ID,E> where E :Entity<ID>
    {
        protected string filename;
        public InFileRepository(string filename,CreateEntity<E> createEntity)
        {
            this.filename = filename;
            if (createEntity != null)
            {
                LoadFromFile(createEntity);
            }

        }
        protected virtual void LoadFromFile(CreateEntity<E> createEntity )
        {
            List<E> list = DataReader.ReadData(filename, createEntity);
            list.ForEach(x=>Save(x));
        }
    }
}
