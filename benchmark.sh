#!/bin/bash

echo "---------------------------------------------------"
echo "  Quick & Dirty benchmarking the microframeworks"
echo "---------------------------------------------------"
echo "Note: You need apache2-utils & gnuplot installed:"
echo "  sudo apt install apache2-utils gnuplot5"
echo ""

./gradlew clean shadowJar

mkdir benchmark-results
cd benchmark-results
ln -s ../spark/grocerylists.json grocerylists.json

for framework in jodd ninja pippo ratpack spark spring5 ; do
  echo ""
  echo "TESTING: $framework"
  echo "--------------------------------------------------------------"
  java -jar ../$framework/build/libs/$framework-1.0-SNAPSHOT-all.jar > /dev/null 2>&1 &
  sleep 5
  ab -n 400000 -c 400 -g benchmark-$framework.tsv "http://localhost:8080/list?_page=1&_perPage=30&_sortDir=DESC&_sortField=id" > benchmark-$framework.txt
  pkill -f SNAPSHOT-all.jar
  sleep 5
  echo "Gnuplotting results"
  cat > benchmark-$framework.gnuplot << EOF
set terminal jpeg size 800,800
set size 1, 1
set output "benchmark-$framework.jpg"
set title "Benchmark $framework"
set key left top
set grid y
set xdata time
set timefmt "%s"
set format x "%S"
set xlabel 'seconds'
set ylabel "response time (ms)"
set datafile separator '\t'
plot "benchmark-$framework.tsv" every ::2 using 2:5 title 'response time' with points
exit
EOF
  gnuplot -c benchmark-$framework.gnuplot
done


echo "--------------------------------------------------------------"
echo "Finished"
echo "Cleaning up"
rm grocerylists.json
echo "Please inspect the log outputs in benchmark-results/:"
ls -1 benchmark-*.txt
cd ..