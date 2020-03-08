using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.model
{
    class Echipa:Entity<int>
    {
        public string Nume { get; set; }

        public override string ToString()
        {
            return ID.ToString() + ' ' + Nume;
        }
    }
}
