using Proiect_MAP.model;
using Proiect_MAP.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.service
{
    class ServiceJucatoriActivi
    {
        IRepository<int, JucatorActiv> repo;
        public ServiceJucatoriActivi(IRepository<int,JucatorActiv> repo)
        {
            this.repo = repo;
        }
        public List<JucatorActiv> FindAllJucatoriActivi()
        {
            return repo.FindAll().ToList();
        }
    }
}
