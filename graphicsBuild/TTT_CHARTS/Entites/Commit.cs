using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace TTT_CHARTS.Entites
{
    public class Commit
    {
        [JsonPropertyName("date")]
        public string Date { get; set; }

        [JsonPropertyName("type")]
        public string Type { get; set; }

        [JsonPropertyName("fileName")]
        public string FileName { get; set; }
    }
}
