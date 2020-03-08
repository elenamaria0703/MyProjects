using Proiect_MAP.model;
using Proiect_MAP.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.service
{
    class ServiceElevi
    {
        private IRepository<int, Elev> repo;
        public ServiceElevi(IRepository<int, Elev> repoElev)
        {
            repo = repoElev;
        }
        public List<Elev> FindAllElevi()
        {
            return repo.FindAll().ToList();
        }
    }
}
