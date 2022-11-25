using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using TTT_CHARTS.Entites.Hypothesises;

namespace TTT_CHARTS.Entites
{
    public class Author
    {
        [JsonPropertyName("name")]
        public string Name { get; set; }

        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("fileCommits")]
        public Dictionary<string, List<Commit>> Commits { get; set; } = new Dictionary<string, List<Commit>>();

        [JsonPropertyName("Stats")]
        public Statistics Stats { get; set; } = new Statistics();

        /// <summary>
        /// посчитать данные для гипотизы один этого автора
        /// </summary>
        public void HypothesisOne()
        {
            List<Hypothesis_One.FileStats_One> FilesStats = new List<Hypothesis_One.FileStats_One>();
            foreach (var file in Commits)
            {
                Hypothesis_One.FileStats_One fs = new Hypothesis_One.FileStats_One();
                fs.FileName = file.Key;
                fs.Stats = new Dictionary<string, Hypothesis_One.FileStats_One.CommitsCounter>();

                foreach (var commit in file.Value)
                {
                    if (commit.Date == null)
                        continue;
                    if (!fs.Stats.ContainsKey(commit.Date))
                    {
                        fs.Stats.Add(commit.Date, new Hypothesis_One.FileStats_One.CommitsCounter());
                    }
                    if (!fs.Stats[commit.Date].Counter.ContainsKey(commit.Type))
                    {
                        fs.Stats[commit.Date].Counter.Add(commit.Type, 0);
                    }
                    fs.Stats[commit.Date].Counter[commit.Type]++;
                }
                FilesStats.Add(fs);
            }
            Stats.HypothesisOne.FilesStats = new List<Hypothesis_One.FileStats_One>(FilesStats);
        }

        /// <summary>
        /// посчитать данные для гипотизы два этого автора
        /// </summary>
        public void HypothesisTwo()
        {
            List<Hypothesis_Two.FileStats_Two> FilesStats = new List<Hypothesis_Two.FileStats_Two>();
            foreach (var file in Commits)
            {
                Hypothesis_Two.FileStats_Two fs = new Hypothesis_Two.FileStats_Two();
                fs.FileName = file.Key;
                fs.Counter = new Dictionary<string, int>();

                foreach (var commit in file.Value)
                {
                    if (!fs.Counter.ContainsKey(commit.Type))
                    {
                        fs.Counter.Add(commit.Type, 0);
                    }
                    fs.Counter[commit.Type]++;
                }
                FilesStats.Add(fs);
            }
            Stats.HypothesisTwo.FilesStats = new List<Hypothesis_Two.FileStats_Two>(FilesStats);
        }

        public override string ToString()
        {
            return Email;
        }
    }
}
