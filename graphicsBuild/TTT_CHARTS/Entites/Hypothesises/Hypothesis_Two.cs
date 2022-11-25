using System;
using System.Collections.Generic;
using System.Diagnostics.Metrics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TTT_CHARTS.Entites;

namespace TTT_CHARTS.Entites.Hypothesises
{
    public class Hypothesis_Two
    {
        public List<FileStats_Two> FilesStats { get; set; } = new List<FileStats_Two>();
        public class FileStats_Two
        {
            public string FileName { get; set; }
            public Dictionary<string, int> Counter { get; set; } = new Dictionary<string, int>();
        }

        public int CountCommits(string filename)
        {
            var fs = FilesStats.Where(e => e.FileName == filename).FirstOrDefault();
            return fs.Counter.Sum(e => e.Value);
        }
        public int CountFix(string filename)
        {
            var fs = FilesStats.Where(e => e.FileName == filename).FirstOrDefault();
            return fs.Counter.Where(e => e.Key == "Fix").Sum(e => e.Value);
        }
        public int CountCommits()
        {
            return FilesStats.Sum(e => e.Counter.Sum(e => e.Value));
        }

        public decimal GetFixProcent(string fileName, List<string> Types, string TargetType = "Fix")
        {
            try
            {
                var file = FilesStats.Where(e => e.FileName == fileName).FirstOrDefault();
                int Total = 0;
                foreach (var item in Types)
                {
                    if (file.Counter.ContainsKey(item))
                        Total += file.Counter[item];
                }
                return (decimal)file.Counter["Fix"] / (decimal)Total;
            }
            catch
            {
                return 1;
            }
        }
    }
}
