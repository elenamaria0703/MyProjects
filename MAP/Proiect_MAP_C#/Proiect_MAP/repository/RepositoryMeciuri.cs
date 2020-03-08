using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class RepositoryMeciuri : InFileRepository<int, Meci>
    {
        public RepositoryMeciuri(string filename, CreateEntity<Meci> createEntity) : base(filename,EntityToFileMapping.CreateMeci)
        {
        }
    }
}
