using Proiect_MAP.model;
using Proiect_MAP.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.service
{
    class ServiceEchipe
    {
        private IRepository<int, Echipa> repo;
        public ServiceEchipe(IRepository<int, Echipa> repo)
        {
            this.repo = repo;
        }
        public List<Echipa> FindAllEchipe()
        {
            return repo.FindAll().ToList();
        }
    }
}
