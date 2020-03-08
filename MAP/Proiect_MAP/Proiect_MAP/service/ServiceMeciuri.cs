using Proiect_MAP.model;
using Proiect_MAP.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.service
{
    class ServiceMeciuri
    {
        IRepository<int, Meci> repo;
        public ServiceMeciuri(IRepository<int,Meci> repo)
        {
            this.repo = repo;
        }
        public List<Meci> FindAllMeciuri()
        {
            return repo.FindAll().ToList();
        }
    }

}
