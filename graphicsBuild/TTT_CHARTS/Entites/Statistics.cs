using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using TTT_CHARTS.Entites.Hypothesises;

namespace TTT_CHARTS.Entites
{
    public class Statistics
    {
        [JsonPropertyName("HypothesisOne")]
        public Hypothesis_One HypothesisOne { get; set; } = new Hypothesis_One();

        [JsonPropertyName("HypothesisTwo")]
        public Hypothesis_Two HypothesisTwo { get; set; } = new Hypothesis_Two();
    }
}
