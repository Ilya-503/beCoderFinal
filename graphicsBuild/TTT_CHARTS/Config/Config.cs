using System.Linq;

namespace TTT_CHARTS.Config
{
    public class Config
    {
        public static Repository reps;
        public static void Initialization(string file)
        {
            reps = new Repository(file);
            reps.CalculateHypotheses();
        }

        public static Entites.Author GetAuthor(string email)
        {
            return reps.Authors.Where(e => e.Email == email).ToList().FirstOrDefault();
        }
    }
}
