class CustomNavigationBar extends HTMLElement {
  constructor() {
      super()
      const shadowRoot = this.attachShadow({mode: 'open'})
      shadowRoot.innerHTML = `
        <style>   
          div.navibar {
            align-items: center;
            background-color: #ccc;
            margin: 0;
            width: 100%;
            height: 100%;
          }
          ::slotted(a) {
            display: block;
            text-align: center;
         }
        </style>
        <div class="navibar">
          <slot></slot>
        </div>`
  }

}

customElements.define('navigation-bar', CustomNavigationBar)