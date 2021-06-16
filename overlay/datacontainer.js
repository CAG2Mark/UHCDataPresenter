/* jshint esversion:6 */

class DataContainer {
    constructor(capacity, width, height, itemHeight, inverted, {top, right, bottom, left}) {
        this.capacity = capacity;
        this.itemHeight = itemHeight;
        let container = this.container = document.createElement("div");
        container.classList.add("data-container");

        this.inverted = inverted;

        document.body.append(container);

        this.items = [];

        container.style.width = width + "px";
        container.style.height = height + "px";

        let cnt = !!top + !!right + !!bottom + !!left;

        if (cnt > 2) console.log("NOTE: Container was created with more than 2 absolute position values set.");

        if (top) container.style.top = top + "px";
        if (right) container.style.right = right + "px";
        if (bottom) container.style.bottom = bottom + "px";
        if (left) container.style.left = left + "px";
    }

    addItem(item) {
        // create item
        let wrap = document.createElement("div");
        wrap.classList.add("data-container-wrap");

        let it = document.createElement("div");
        it.classList.add("data-container-item");

        wrap.appendChild(it);

        
        wrap.style.marginTop = (this.inverted ? "" : "-") + 
            (wrap.style.height = 
                it.style.height = this.itemHeight + "px");

        wrap.style.opacity = 0;
        
        it.append(item);

        if (this.inverted) {
            this.container.append(wrap);
            // items functions as a queue
            this.items.push(wrap);
        }
        else {
            this.container.insertBefore(wrap, this.container.firstChild);
            // items functions as a backwards queue
            this.items.splice(0, 0, wrap);
        }

        this.animateIn(wrap);

        if (this.items.length > this.capacity) {
            if (this.inverted) {
                this.removeAtIndex(0, true);
            }
            else {
                this.removeAtIndex(this.capacity, true);
            }
        }           
    }

    animateIn(wrap) {
        console.log(wrap);

        wrap.style.display = "block";
        wrap.style.height = this.itemHeight + "px";

        $(wrap).stop(true, false).animate({
            marginTop: 0,
            opacity: 1,
        }, 400, () => {
            wrap.style.display = "block";
        });
    }

    removeItem(item) {
        var index;
        if ((index = this.items.findIndex(x => x.firstChild.firstChild === item)) != -1) {
            this.removeAtIndex(index);
        }            
    }

    removeAtIndex(index, onlyHide = false) {
        if (index < 0) return;

        let removed = !onlyHide ? 
            this.items.splice(index, 1)[0] :
            this.items[index];

        $(removed).animate({
            opacity: 0,
            height: 0,
        }, 400, () => {
            if (!onlyHide) {
                this.container.removeChild(removed);
            } else {
                removed.style.display = "none";
            }
        });

        if (!onlyHide) {
            // try to reshow one of the hidden items
            if (this.items.length >= this.capacity) {                
                this.animateIn(
                    this.inverted ? this.items[0] : this.items[this.capacity - 1]
                );
            }
        }
    }

    clear() {
        for (var i = 0; i < this.items.length; ++i) this.removeAtIndex(i);
    }
}