using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proiect_MAP.model
{
    enum Tip
    {
        Participant,Rezerva
    }
    class JucatorActiv:Entity<int>
    {
        public int IdJucator { get; set; }
        public int IDMeci { get; set; }
        public int NrPctInscrise { get; set; }
        public Tip Tip { get; set; }

        public override string ToString()
        {
            return IdJucator.ToString()+' '+IDMeci.ToString()+' '+NrPctInscrise.ToString()+' '+Tip;
        }
    }


}
