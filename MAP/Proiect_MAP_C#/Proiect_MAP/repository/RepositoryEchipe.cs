using Proiect_MAP.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.repository
{
    class RepositoryEchipe:InFileRepository<int,Echipa>
    {
        public RepositoryEchipe(string filename):base(filename, EntityToFileMapping.CreateEchipa)
        {

        }
    }
}
