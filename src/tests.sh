#!/bin/sh

for entry in "ttp/instances/sample"/* 
do
   if [ -f "$entry" ];then
      java ttp.Heuristics.TTPInstance $entry
   fi
done
