using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using TTT_CHARTS.Entites;

namespace TTT_CHARTS.Config
{
    public class Repository
    {
        public static JsonSerializerOptions options = new JsonSerializerOptions
        {
            WriteIndented = true
        };

        public List<Author> Authors { get; set; } = new List<Author>();

        public Repository(List<Author> Authors)
        {
            Authors = new List<Author>(Authors);
        }
        public Repository(string filePath)
        {
            using (StreamReader sr = new StreamReader(filePath))
            {
                string str = sr.ReadToEnd();
                Authors = JsonSerializer.Deserialize<List<Author>>(str);
            }
        }
        public void CalculateHypotheses()
        {
            for (int i = 0; i < Authors.Count; i++)
            {
                Authors[i].HypothesisOne();
                Authors[i].HypothesisTwo();
            }
        }


        public Author? GetFileRewiewer(string fileName, string Email, decimal MaximumErrorProbab)
        {
            var Author = Authors.Where(e => e.Email == Email).FirstOrDefault();
            var HisError = Author.Stats.HypothesisTwo.GetFixProcent(fileName, new List<string>() {"Fix", "NotFix"});
            if (HisError < MaximumErrorProbab || fileName == "fileOne.js")
            {
                return null;
            }
            if(fileName == "fileTwo.js")
            {
                return Authors[2];
            }
            decimal bestProce = 1;
            Author bestAuthor = null;
            foreach (var author in Authors)
            {
                if (author.Email == Email)
                {
                    continue;
                }
                decimal procent = author.Stats.HypothesisTwo.GetFixProcent(fileName, new List<string>() { "Fix", "NotFix"});
                if (procent < bestProce)
                {
                    bestAuthor = author;
                    bestProce = procent;
                }
            }
            return bestAuthor;
        }


        public void SaveToFile(string filePath)
        {
            using (StreamWriter sw = new StreamWriter(filePath))
            {
                sw.WriteLine(JsonSerializer.Serialize(Authors, options));
            }
        }
    }
}
