import {Component, OnChanges, Renderer2, ElementRef, Input, Output, EventEmitter} from '@angular/core';

declare var cytoscape: any;

//#424242; 
@Component({
  selector: 'app-blueprint-graph',
  template: '<div id="cy"></div>',
    styles: [`#cy {
        background: #fff;
        height: 100%;
        width: 100%;
        position: relative;
        left: 0;
        top: 0;
    }`]
})

export class BlueprintGraphComponent implements OnChanges {

  @Input() public elements: any;
  @Input() public style: any;
  @Input() public layout: any;
  @Input() public zoom: any;
  @Input() public position: any;

  @Output() select: EventEmitter<any> = new EventEmitter<any>();

  constructor(private renderer : Renderer2, private el: ElementRef) {
    this.layout = this.layout || {
      name: 'cose',
      //directed: true,
      padding: 10
    };

    this.zoom = this.zoom || {
      min: 0.1,
      max: 1.5
    };

    this.position = function(node) {
      return {
       row: node.data('row'),
       col: node.data('col')
      }
    };

    this.style = this.style || cytoscape.stylesheet()
      .selector('node')
        .css({
          'shape': 'data(shapeType)',
          //'content': 'data(name)',
          //'text-valign': 'center',
          'text-outline-width': 0,
          'text-width': 2,
          //'text-outline-color': '#000',
          'background-color': 'data(colorCode)',
          'color': '#000',
          'label': 'data(name)',
          'text-valign': 'top',
          'text-halign': 'center'
        })
      .selector(':selected')
        .css({
          'border-width': 3,
          'border-color': 'black'
        })
      .selector('edge')
        .css({
          'curve-style': 'bezier',
          'opacity': 0.666,
          'width': 'mapData(strength, 70, 100, 2, 6)',
          'target-arrow-shape': 'circle',
          'source-arrow-shape': 'circle',
          'line-color': 'data(colorCode)',
          'source-arrow-color': 'data(colorCode)',
          'target-arrow-color': 'data(colorCode)'
        })
      .selector('edge.questionable')
        .css({
          'line-style': 'dotted',
          'target-arrow-shape': 'diamond'
        })
      .selector('.faded')
        .css({
          'opacity': 0.25,
          'text-opacity': 0
        })
      .selector('.vnf')
				.css({
					'background-image': 'assets/images/vnf_icon_80.png',
					'width': 80,//'mapData(weight, 40, 80, 20, 60)',
					'height': 80
				})
			.selector('.pnf')
				.css({
					'background-image': 'assets/images/pnf_icon_80.png',
					'width': 80,//'mapData(weight, 40, 80, 20, 60)',
					'height': 80
				})
			.selector('.net')
				.css({
					'background-image': 'assets/images/net_icon_50.png',
					'width': 50,//'mapData(weight, 40, 80, 20, 60)',
					'height': 50
				})
			.selector('.sap')
				.css({
					'background-image': 'assets/images/sap_icon_grey_50.png',
					'width': 50,//'mapData(weight, 40, 80, 20, 60)',
					'height': 50
        })
      .selector('.vdu')
				.css({
					'background-image': 'assets/images/vdu_icon_80.png',
					'width': 80,//'mapData(weight, 40, 80, 20, 60)',
					'height': 80
				})
			.selector('.extcp')
				.css({
					'background-image': 'assets/images/cp_icon_50.png',
					'width': 50,//'mapData(weight, 40, 80, 20, 60)',
					'height': 50
				})
			.selector('.top-left')
				.css({
					'text-valign': 'top',
					'text-halign': 'left'
				})
			.selector('.top-right')
				.css({
					'text-valign': 'top',
					'text-halign': 'right'
				})
			.selector('.bottom-center')
				.css({
					'text-valign': 'bottom',
					'text-halign': 'center'
				});
  }

  ngOnChanges(): any {
    this.render();
    //console.log(this.el.nativeElement);
  }

  public render() {
    let cy_container = this.renderer.selectRootElement("#cy");
    let localselect = this.select;
    let cy = cytoscape({
      container : cy_container,
      layout: this.layout,
      minZoom: this.zoom.min,
      maxZoom: this.zoom.max,
      style: this.style,
      elements: this.elements,
    });

    cy.on('tap', 'node', function(e) {
      var node = e.target;
      var neighborhood = node.neighborhood().add(node);

      cy.elements().addClass('faded');
      neighborhood.removeClass('faded');
      localselect.emit(node.data('name'));
    });

    cy.on('tap', function(e) {
      if (e.target === cy) {
        cy.elements().removeClass('faded');
      }
    });
  }
}
