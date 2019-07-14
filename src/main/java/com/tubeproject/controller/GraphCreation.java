package com.tubeproject.controller;

import com.tubeproject.model.ContextMap;
import com.tubeproject.model.DatabaseConnection;
import com.tubeproject.model.Select;
import com.tubeproject.model.requests.GetAllConnections;
import com.tubeproject.model.requests.GetAllLinesWithStationsRequest;
import com.tubeproject.model.requests.GetAllStationsRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GraphCreation {
    private List<Node> nodes = new ArrayList<Node>();
    private List<Station> stations = new ArrayList<Station>();


    public GraphCreation() {
    }


    public List<Node> getData() throws SQLException {
        //Toujours initialiser la context map
        Map<String, Object> ctxMap = ContextMap.getContextMap();
        List<Line> lines = new ArrayList<Line>();

        DatabaseConnection.DatabaseOpen();
        GetAllConnections gac = new GetAllConnections();
        Select s = new Select(gac);
        Map<Station, List<Connection>> map = (Map<Station, List<Connection>>) s.select().get();
        System.out.println(map);

        nodes = map
                .keySet()
                .stream()
                .map(station -> new Node(station.getName(), station.getLatitude(), station.getLongitude()))
                .collect(Collectors.toList());

        lines = this.getLine();

        for (Node node : nodes)
        {
            for (Line line : lines)
            {
                for (Station station : line.getStations())
                {
                    if (station.getName().equals(node.getValue()))
                    {
                        node.addLines(line.getName());
                    }
                }
            }
            //System.out.println("station: " + node.getValue() + " line: " + node.getLines());
        }

        for (Station station : map.keySet()){
            List<Edge> edges = new ArrayList<Edge>();
            for (Node node : nodes)
            {
              if (station.getName().equals(node.getValue()))
              {
                  for (Connection connection : map.get(station))
                  {
                      for (Node subNode : nodes)
                      {
                          if (subNode.getValue().equals(connection.getStation().getName()))
                          {
                              edges.add(new Edge (subNode, connection.getDuration()));
                          }
                      }
                  }
                  node.setAdjacencies(edges);
                  /*System.out.println("Name of the station is: " + station.getName());
                  for (Edge e : node.getAdjacencies())
                  {
                      System.out.println(e.toString());
                  }
                  System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");*/
              }
            }
        }

        return nodes;
    }

    public void getStation() throws SQLException {

        DatabaseConnection.DatabaseOpen();
        GetAllStationsRequest gas = new GetAllStationsRequest();
        Select select = new Select(gas);
        Optional<?> opt = select.select();
        if (opt.isPresent()) {
            stations = (List<Station>)opt.get();
            System.out.println(stations.size());
        }
        else {
            System.out.println("Impossible to retrieve Station");
        }
    }

    public List<Line> getLine() throws SQLException {
            DatabaseConnection.DatabaseOpen();

            GetAllLinesWithStationsRequest obj = new GetAllLinesWithStationsRequest();
            Select select = new Select(obj);
            Optional<?> opt = select.select();
            if (opt.isPresent()) {
                List<Line> l = new ArrayList<>();
                l = (List<Line>)opt.get();
                //this.displayStationLocation(l);
                return l;
            }
            else {
                System.out.println("Impossible to retrieve lines");
            }
            DatabaseConnection.DatabaseClose();
            System.out.println("Error: Connection to the server failed.");
        return null;
    }


    public void displayStationLocation (List<Line> l){

        for (Line value : l){
            System.out.println("line: " + value.getName());
            for (Station station : value.getStations())
            {
                System.out.println("name is: " + station.getName() + " location: " + station.getLatitude() + " " + station.getLongitude());
            }
        }

    }
}
