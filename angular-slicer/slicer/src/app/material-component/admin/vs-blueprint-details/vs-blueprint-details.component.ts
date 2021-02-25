/*
import { Component, OnInit, ViewChild } from '@angular/core';
import { VsBlueprintDetailsService } from '../../../services/vs-blueprint-details.service';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { BlueprintsVsDetailsItemKV, BlueprintsVsDetailsDataSource } from './vs-blueprint-details.datasource';
import { VsBlueprintInfo } from '../vs-blueprint/vs-blueprint-info';
import { VsBlueprintsService } from '../../../services/vs-Blueprints.service';
import { VsDescriptorsService } from '../../../services/vs-descriptors.service';
import { VsDescriptorInfo } from '../../admin/vs-descriptor/vs-descriptor-info';

@Component({
  selector: 'app-vs-blueprint-details',
  templateUrl: './vs-blueprint-details.component.html',
  styles: [`
    app-blueprint-graph {
      height: 70vh;
      float: left;
      width: 100%;
      position: relative;
    }`]
})
export class VsBlueprintDetailsComponent implements OnInit {

  node_name: string;

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<BlueprintsVsDetailsItemKV>;
  dataSource: BlueprintsVsDetailsDataSource;

  graphData = {
    nodes: [],
    edges: []
  };

  tableData: BlueprintsVsDetailsItemKV[] = [];

  displayedColumns = ['key', 'value'];

  constructor(public vsbDetailservice: VsBlueprintDetailsService,
              private blueprintsVsService: VsBlueprintsService,
              private vsDescriptorService: VsDescriptorsService) {}

  ngOnInit() {
    var vsbId = localStorage.getItem('vsbId');
    this.dataSource = new BlueprintsVsDetailsDataSource(this.vsbDetailservice._vsBlueprintDetailsItems);
    this.getVsBlueprint(vsbId);
  }

  nodeChange(event: any) {
      this.node_name = event;
  }

  getVsBlueprint(vsbId: string) {
    this.blueprintsVsService.getVsBlueprint(vsbId).subscribe((vsBlueprintInfo: VsBlueprintInfo) => {
    this.vsDescriptorService.getVsDescriptorsData().subscribe((vsDescriptors: VsDescriptorInfo[]) => {
    var vsBlueprint = vsBlueprintInfo['vsBlueprint'];



        var atomicComponents = vsBlueprint['atomicComponents'];


        var endPoints = vsBlueprint['endPoints'];
 

        var atomicComponentsCps = [];

        for (var i = 0; i < atomicComponents.length; i++) {
          this.graphData.nodes.push(
            { data: { id: atomicComponents[i]['componentId'], name: atomicComponents[i]['componentId'], weight: 70, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center vnf' }
          );

          atomicComponentsCps.push(...atomicComponents[i]['endPointsIds']);
          console.log(atomicComponentsCps);
        }

        var sapCps = [];
        for (var i = 0; i < endPoints.length; i ++) {
          if (!(atomicComponentsCps.includes(endPoints[i]['endPointId']))) {
            sapCps.push(endPoints[i]['endPointId']);
            this.graphData.nodes.push(
              { data: { id: endPoints[i]['endPointId'], name: endPoints[i]['endPointId'], weight: 50, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center sap' }
            );
          }
        }

        var connectivityServices = vsBlueprint['connectivityServices'];

        for (var i = 0; i < connectivityServices.length; i++) {
          this.graphData.nodes.push(
            { data: { id: "conn_service_" + i, name: connectivityServices[i]['name'], weight: 50, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center net' }
          );

          for (var j = 0; j < atomicComponents.length; j++) {
            for (var h = 0; h < atomicComponents[j]['endPointsIds'].length; h++) {
              if (connectivityServices[i]['endPointIds'].includes(atomicComponents[j]['endPointsIds'][h])) {
                this.graphData.edges.push(
                  { data: { source: atomicComponents[j]['componentId'], target: "conn_service_" + i, colorCode: 'black', strength: 70 } }
                );
              }
            }
          }

          for (var j = 0; j < sapCps.length; j++) {
            if (connectivityServices[i]['endPointIds'].includes(sapCps[j])) {
              this.graphData.edges.push(
                { data: { source: sapCps[j], target: "conn_service_" + i, colorCode: 'grey', strength: 70 } }
              );
            }
          }
        }
        console.log("this.graphData.edges",this.graphData.edges)
        this.vsbDetailservice.updateVSBGraph(this.graphData);
          });
        });
      }


      getVsBlueprintOld(vsbId: string) {
        this.blueprintsVsService.getVsBlueprint(vsbId).subscribe((vsBlueprintInfo: VsBlueprintInfo) => {
              this.vsDescriptorService.getVsDescriptorsData().subscribe((vsDescriptors: VsDescriptorInfo[]) => {
            //console.log(vsBlueprintInfo);
            var vsBlueprint = vsBlueprintInfo['vsBlueprint'];
            this.tableData.push({key: "Name", value: [vsBlueprint['name']]});
            //this.tableData.push({key: "Id", value: [vsBlueprint['blueprintId']]});
            this.tableData.push({key: "Version", value: [vsBlueprint['version']]});
            this.tableData.push({key: "Description", value: [vsBlueprint['description']]});
            var values = [];
            if( vsBlueprint['parameters'] !== undefined){
              for (var i = 0; i < vsBlueprint['parameters'].length; i++) {
                values.push(vsBlueprint['parameters'][i]['parameterName']);
              }
              this.tableData.push({key: "Parameters", value: values});
            }
            values = [];
    
            var atomicComponents = vsBlueprint['atomicComponents'];
            for (var i = 0; i < atomicComponents.length; i++) {
              if(atomicComponents[i]['placement'] !== undefined && atomicComponents[i]['placement'] !== ''){
                values.push(atomicComponents[i]['componentId'] + " (" + atomicComponents[i]['placement'].toLowerCase() + ")");
              } else {
                values.push(atomicComponents[i]['componentId']);
              }
            }
            this.tableData.push({key: "Components", value: values});
    
            values = [];
    
            var endPoints = vsBlueprint['endPoints'];
            for (var i = 0; i < endPoints.length; i++) {
              values.push(endPoints[i]['endPointId']);
            }
            this.tableData.push({key: "Endpoints", value: values});

            values = [];

            var atomicComponentsCps = [];
    
            for (var i = 0; i < atomicComponents.length; i++) {
              this.graphData.nodes.push(
                { data: { id: atomicComponents[i]['componentId'], name: atomicComponents[i]['componentId'], weight: 70, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center vnf' }
              );
    
              atomicComponentsCps.push(...atomicComponents[i]['endPointsIds']);
              console.log(atomicComponentsCps);
            }
    
            var sapCps = [];
            for (var i = 0; i < endPoints.length; i ++) {
              if (!(atomicComponentsCps.includes(endPoints[i]['endPointId']))) {
                sapCps.push(endPoints[i]['endPointId']);
                this.graphData.nodes.push(
                  { data: { id: endPoints[i]['endPointId'], name: endPoints[i]['endPointId'], weight: 50, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center sap' }
                );
              }
            }
    
            var connectivityServices = vsBlueprint['connectivityServices'];
    
            for (var i = 0; i < connectivityServices.length; i++) {
              this.graphData.nodes.push(
                { data: { id: "conn_service_" + i, name: connectivityServices[i]['name'], weight: 50, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center net' }
              );
    
              for (var j = 0; j < atomicComponents.length; j++) {
                for (var h = 0; h < atomicComponents[j]['endPointsIds'].length; h++) {
                  if (connectivityServices[i]['endPointIds'].includes(atomicComponents[j]['endPointsIds'][h])) {
                    this.graphData.edges.push(
                      { data: { source: atomicComponents[j]['componentId'], target: "conn_service_" + i, colorCode: 'black', strength: 70 } }
                    );
                  }
                }
              }
    
              for (var j = 0; j < sapCps.length; j++) {
                if (connectivityServices[i]['endPointIds'].includes(sapCps[j])) {
                  this.graphData.edges.push(
                    { data: { source: sapCps[j], target: "conn_service_" + i, colorCode: 'grey', strength: 70 } }
                  );
                }
              }
            }
    
            //console.log(this.tableData);
            //console.log(this.graphData);
            this.vsbDetailservice.updateVSBTable(this.tableData);
            this.vsbDetailservice.updateVSBGraph(this.graphData);
           // this.dataSource = new BlueprintsVsDetailsDataSource(this.tableData);
           // this.dataSource.sort = this.sort;
           // this.table.dataSource = this.dataSource;
              });
            });
          
      }
  }

*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { VsBlueprintDetailsService } from '../../../services/vs-blueprint-details.service';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { BlueprintsVsDetailsItemKV, BlueprintsVsDetailsDataSource } from './vs-blueprint-details.datasource';
import { VsBlueprintInfo } from '../vs-blueprint/vs-blueprint-info';
import { VsBlueprintsService } from '../../../services/vs-Blueprints.service';
import { VsDescriptorsService } from '../../../services/vs-descriptors.service';
import { VsDescriptorInfo } from '../../admin/vs-descriptor/vs-descriptor-info';


@Component({
  selector: 'app-vs-blueprint-details',
  templateUrl: './vs-blueprint-details.component.html',
  styles: [`
    app-blueprint-graph {
      height: 70vh;
      float: left;
      width: 100%;
      position: relative;
    }`]
})
export class VsBlueprintDetailsComponent implements OnInit {

  node_name: string;

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<BlueprintsVsDetailsItemKV>;
  dataSource: BlueprintsVsDetailsDataSource;

  graphData = {
    nodes: [],
    edges: []
  };

  tableData: BlueprintsVsDetailsItemKV[] = [];

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['key', 'value'];

  constructor(public vsbDetailservice: VsBlueprintDetailsService,
    private blueprintsVsService: VsBlueprintsService,
    private vsDescriptorService: VsDescriptorsService) {}

  ngOnInit() {
    var vsbId = localStorage.getItem('vsbId');
    this.dataSource = new BlueprintsVsDetailsDataSource(this.vsbDetailservice._vsBlueprintDetailsItems);
    this.getVsBlueprint(vsbId);
  }

  nodeChange(event: any) {
      this.node_name = event;
  }

  getVsBlueprint(vsbId: string) {
    this.blueprintsVsService.getVsBlueprint(vsbId).subscribe((vsBlueprintInfo: VsBlueprintInfo) => {
          this.vsDescriptorService.getVsDescriptorsData().subscribe((vsDescriptors: VsDescriptorInfo[]) => {
        var vsBlueprint = vsBlueprintInfo['vsBlueprint'];
        this.tableData.push({key: "Name", value: [vsBlueprint['name']]});
        //this.tableData.push({key: "Id", value: [vsBlueprint['blueprintId']]});
        this.tableData.push({key: "Version", value: [vsBlueprint['version']]});
        this.tableData.push({key: "Description", value: [vsBlueprint['description']]});
        var values = [];
        if( vsBlueprint['parameters'] !== undefined){
          for (var i = 0; i < vsBlueprint['parameters'].length; i++) {
            values.push(vsBlueprint['parameters'][i]['parameterName']);
          }
          this.tableData.push({key: "Parameters", value: values});
        }
        values = [];

        var atomicComponents = vsBlueprint['atomicComponents'];
        for (var i = 0; i < atomicComponents.length; i++) {
          if(atomicComponents[i]['placement'] !== undefined && atomicComponents[i]['placement'] !== '' && atomicComponents[i]['placement'] !== null){
            values.push(atomicComponents[i]['componentId'] + " (" + atomicComponents[i]['placement'].toLowerCase() + ")");
          } else {
            values.push(atomicComponents[i]['componentId']);
          }
        }
       
        this.tableData.push({key: "Components", value: values});

        values = [];

        var endPoints = vsBlueprint['endPoints'];
        for (var i = 0; i < endPoints.length; i++) {
          values.push(endPoints[i]['endPointId']);
        }
        this.tableData.push({key: "Endpoints", value: values});

        values = [];
      
        var atomicComponentsCps = [];

        for (var i = 0; i < atomicComponents.length; i++) {
          
          this.graphData.nodes.push(
            { data: { id: atomicComponents[i]['componentId'], name: atomicComponents[i]['componentId'], weight: 70, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center vnf' }
          );
        
          atomicComponentsCps.push(...atomicComponents[i]['endPointsIds']);

        }
        
        var sapCps = [];
        for (var i = 0; i < endPoints.length; i ++) {
          if (!(atomicComponentsCps.includes(endPoints[i]['endPointId']))) {
            sapCps.push(endPoints[i]['endPointId']);
            this.graphData.nodes.push(
              { data: { id: endPoints[i]['endPointId'], name: endPoints[i]['endPointId'], weight: 50, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center sap' }
            );
          }
        }

        var connectivityServices = vsBlueprint['connectivityServices'];

        for (var i = 0; i < connectivityServices.length; i++) {
          this.graphData.nodes.push(
            { data: { id: "conn_service_" + i, name: connectivityServices[i]['name'], weight: 50, colorCode: 'white', shapeType: 'ellipse' }, classes: 'bottom-center net' }
          );

          for (var j = 0; j < atomicComponents.length; j++) {
            for (var h = 0; h < atomicComponents[j]['endPointsIds'].length; h++) {
              if (connectivityServices[i]['endPointIds'].includes(atomicComponents[j]['endPointsIds'][h])) {
                this.graphData.edges.push(
                  { data: { source: atomicComponents[j]['componentId'], target: "conn_service_" + i, colorCode: 'black', strength: 70 } }
                );
              }
            }
          }

          for (var j = 0; j < sapCps.length; j++) {
            if (connectivityServices[i]['endPointIds'].includes(sapCps[j])) {
              this.graphData.edges.push(
                { data: { source: sapCps[j], target: "conn_service_" + i, colorCode: 'grey', strength: 70 } }
              );
            }
          }
        }

        //console.log(this.tableData);
        //console.log(this.graphData);
        this.vsbDetailservice.updateVSBTable(this.tableData);
        this.vsbDetailservice.updateVSBGraph(this.graphData);
        this.dataSource = new BlueprintsVsDetailsDataSource(this.tableData);
        this.dataSource.sort = this.sort;
        this.table.dataSource = this.dataSource;
          });
        });
      
  }
}
