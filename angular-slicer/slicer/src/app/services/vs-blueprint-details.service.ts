import { Injectable } from '@angular/core';
import { BlueprintsVsDetailsItemKV } from '../material-component/admin/vs-blueprint-details/vs-blueprint-details.datasource';

@Injectable({
  providedIn: 'root'
})
export class  VsBlueprintDetailsService {
  _graphData: any = {
    nodes: [] = [],
    edges: [] = []
  };
  _vsBlueprintDetailsItems: BlueprintsVsDetailsItemKV[] = [];

  _vsBlueprintId: string = "";

  public updateVSBGraph(data: any) {
    this._graphData = data;
  }

  public updateVSBTable(data: BlueprintsVsDetailsItemKV[]) {
    //console.log(data);
    this._vsBlueprintDetailsItems = data;
  }

  public updateVSBId(data: string) {
    this._vsBlueprintId = data;
    localStorage.setItem('vsbId', data);
  }

  private addNode(node: any) {
    this._graphData.nodes.push(node);
  }

  private addEdge(edge: any) {
    this._graphData.edges.push(edge);
  }

  public get graphData() {
    return this._graphData;
  }

  public get VsBlueprintDetailsItems() {
    return this._vsBlueprintDetailsItems;
  }

  public get VsBlueprintId() {
    return this._vsBlueprintId;
  }
}
