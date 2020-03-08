using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.model
{
    class Meci:Entity<int>
    {
        public Echipa Echipa1 { get; set; }
        public Echipa Echipa2 { get; set; }
        public DateTime Data { get; set; }

        public override bool Equals(object obj)
        {
            return obj is Meci meci && ID.Equals(meci.ID);      
        }

        public override string ToString()
        {
            return Echipa1.Nume +' '+Echipa2.Nume+' '+Data;
        }
    }
}
