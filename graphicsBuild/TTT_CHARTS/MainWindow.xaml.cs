using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using TTT_CHARTS.Entites;

namespace TTT_CHARTS
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            Config.Config.Initialization("RESULT.json");
            InitializeComponent();

            int height = 32;
            int width = 160;

            for (int i = 0; i < Config.Config.reps.Authors.Count; i++)
            {
                var btn1 = new Button()
                {
                    Width = width,
                    Height = height,
                    Margin = new Thickness(0, 10 + (60 * i), 25 + width, 10),
                    HorizontalAlignment = HorizontalAlignment.Right,
                    VerticalAlignment = VerticalAlignment.Top,
                    Content = Config.Config.reps.Authors[i],
                    Name = "User_" + (i + 1) + "_H1",
                    Background = new SolidColorBrush(Color.FromRgb(0xCE, 0x7D, 0xAB))
                };
                var btn2 = new Button()
                {
                    Width = width,
                    Height = height,
                    Margin = new Thickness(0, 10 + (60 * i), 15, 10),
                    HorizontalAlignment = HorizontalAlignment.Right,
                    VerticalAlignment = VerticalAlignment.Top,
                    Content = Config.Config.reps.Authors[i],
                    Name = "User_" + (i + 1) + "_H2",
                    Background = new SolidColorBrush(Color.FromRgb(0xCE, 0x7D, 0xAB))

                };
                btn1.Click += Button_Click_h1;
                btn2.Click += Button_Click_h2;
                grid2.Children.Add(btn1);
                grid2.Children.Add(btn2);
            }
        }

        private void Button_Click_h1(object sender, RoutedEventArgs e)
        {
            Button b = (Button)sender;
            var author = (Author)b.Content;
            var f = author.Stats.HypothesisOne.GetBadFile();
            D_H1 = new List<Data_H1>();
            D_H12 = new List<Data_H12>();
            foreach (var item in f.Stats)
            {
                D_H1.Add(new Data_H1() { Id = item.Key, Value = item.Value.Counter.Sum(e => e.Value) });
                D_H12.Add(new Data_H12() { Id2 = item.Key, Value2 = item.Value.Counter.Where(e => e.Key == "Fix").Sum(e => e.Value) });
            }
            D_H1 = D_H1.OrderBy(e => e.Id).ToList();
            D_H12 = D_H12.OrderBy(e => e.Id2).ToList();
            ITEM_1.ItemsSource = D_H1;
            ITEM_2.ItemsSource = D_H12;
        }

        private void Button_Click_h2(object sender, RoutedEventArgs e)
        {
            Button b = (Button)sender;
            var author = (Author)b.Content;
            D_H2 = new List<Data_H2>();
            D_H22 = new List<Data_H22>();
            foreach (var item in author.Stats.HypothesisTwo.FilesStats)
            {
                if (author.Stats.HypothesisTwo.CountCommits(item.FileName) > 35)
                {
                    D_H2.Add(new Data_H2()
                    {
                        Id = author.Stats.HypothesisTwo.CountCommits(item.FileName),
                        Value = author.Stats.HypothesisTwo.CountCommits(item.FileName),
                    });
                    D_H22.Add(new Data_H22()
                    {
                        Id2 = author.Stats.HypothesisTwo.CountCommits(item.FileName),
                        Value2 = author.Stats.HypothesisTwo.CountFix(item.FileName),
                    });
                }

            }
            D_H2 = D_H2.OrderBy(e => e.Id).ToList();
            D_H22 = D_H22.OrderBy(e => e.Id2).ToList();
            ITEM_1.ItemsSource = D_H2;
            ITEM_2.ItemsSource = D_H22;
        }

        public List<Data_H1> D_H1 { get; set; } = new List<Data_H1>();
        public class Data_H1
        {
            public string Id { get; set; }
            public int Value { get; set; }
        }
        public List<Data_H12> D_H12 { get; set; } = new List<Data_H12>();
        public class Data_H12
        {
            public string Id2 { get; set; }
            public int Value2 { get; set; }
        }


        public List<Data_H2> D_H2 { get; set; } = new List<Data_H2>();
        public class Data_H2
        {
            public int Id { get; set; }
            public int Value { get; set; }
        }
        public List<Data_H22> D_H22 { get; set; } = new List<Data_H22>();
        public class Data_H22
        {
            public int Id2 { get; set; }
            public int Value2 { get; set; }
        }

        private void GetRewiewer(object sender, RoutedEventArgs e)
        {
            var email = Email.Text;
            var fileName = FileName.Text;
            var a = Config.Config.reps.GetFileRewiewer(fileName, email, 0.1m);
            if (a == null)
            {
                Output.Foreground = Brushes.Green;
                Output.Text = "Этот комит не требует ревью";
                return;
            }
            Output.Foreground = Brushes.Red;
            Output.Text = "Для ревью рекомендовано обратиться к: " + a.Email;
            return;
        }
    }
}
