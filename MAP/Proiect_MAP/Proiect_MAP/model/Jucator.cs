using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.model
{
    class Jucator:Elev
    {
        public Echipa Echipa { get; set; }

        public override string ToString()
        {
            return base.ToString()+' '+Echipa.Nume;
        }
    }


}
