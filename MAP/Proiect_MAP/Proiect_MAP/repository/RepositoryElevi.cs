using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class RepositoryElevi:InFileRepository<int,Elev>
    {
        public RepositoryElevi(string filename) : base(filename, EntityToFileMapping.CreateElev)
        {

        }
    }
}
