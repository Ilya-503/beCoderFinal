using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TTT_CHARTS.Entites.Hypothesises
{
    public class Hypothesis_One
    {
        public List<FileStats_One> FilesStats { get; set; } = new List<FileStats_One>();
        public class FileStats_One
        {
            public string FileName { get; set; }
            public Dictionary<string, CommitsCounter> Stats { get; set; } = new Dictionary<string, CommitsCounter>();
            public class CommitsCounter
            {
                public Dictionary<string, int> Counter { get; set; } = new Dictionary<string, int>();

                public decimal GetProcentFix()
                {
                    if (!Counter.ContainsKey("Fix") || !Counter.ContainsKey("NotFix"))
                        return 0;
                    return ((decimal)Counter["Fix"]) / ((decimal)Counter["Fix"] + (decimal)Counter["NotFix"]);
                }
            }

        }


        public FileStats_One GetBadFile()
        {
            var badC = 0;
            FileStats_One badF = new FileStats_One();
            foreach (var item in FilesStats)
            {
                try
                {
                    var cF = item.Stats.Sum(e => e.Value.Counter.Where(e => e.Key == "Fix").Sum(e => e.Value));
                    if (cF > badC)
                    {
                        badC = cF;
                        badF = item;
                    }
                }
                catch
                {

                }
            }
            return badF;
        }

        public FileStats_One GetAllStats()
        {
            FileStats_One badF = new FileStats_One();
            foreach (var item in FilesStats)
            {
                foreach (var s in item.Stats)
                {
                    try
                    {
                        if (!badF.Stats.ContainsKey(s.Key))
                            badF.Stats.Add(s.Key, new FileStats_One.CommitsCounter());
                        badF.Stats[s.Key].Counter["Fix"] += s.Value.Counter["Fix"];
                        badF.Stats[s.Key].Counter["NotFix"] += s.Value.Counter["NotFix"];
                    }
                    catch
                    {

                    }
                }
            }
            return badF;
        }

    }
}
