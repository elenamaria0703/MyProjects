using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class InMemoryRepository<ID, E> : IRepository<ID, E> where E : Entity<ID>
    {
        protected IDictionary<ID, E> entities = new Dictionary<ID, E>();

        public IEnumerable<E> FindAll()
        {
            return entities.Values.ToList<E>();
        }

        public E FindOne(ID id)
        {
            if (id == null) return null;
            if (!entities.ContainsKey(id)) return null;
            return entities[id];
        }

        public E Save(E entity)
        {
            if (entity == null)
                throw new ArgumentNullException("entity must not be null");
            if (entities.ContainsKey(entity.ID)) return entity;
            entities[entity.ID] = entity;
            return default(E);
        }
    }
}
