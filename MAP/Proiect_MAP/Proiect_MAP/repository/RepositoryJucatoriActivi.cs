using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class RepositoryJucatoriActivi : InFileRepository<int, JucatorActiv>
    {
        public RepositoryJucatoriActivi(string filename, CreateEntity<JucatorActiv> createEntity) : base(filename, createEntity)
        {
        }
    }
}
