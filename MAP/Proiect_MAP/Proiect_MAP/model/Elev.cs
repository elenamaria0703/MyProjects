using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.model
{
    class Elev:Entity<int>
    {
        public string Nume { get; set; }
        public string Scoala { get; set; }

        public override string ToString()
        {
            return ID.ToString()+' '+Nume;
        }
    }


}
