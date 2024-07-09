import React from 'react';

export function parseHtmlString(htmlString) {
  const parser = new DOMParser();
  const doc = parser.parseFromString(htmlString, 'text/html');

  const sanitizeContent = (content) => {
    const tempDiv = document.createElement('div');
    tempDiv.textContent = content;
    return tempDiv.innerHTML;
  };

  const createElement = (node) => {
    if (node.nodeType === Node.TEXT_NODE) {
      return node.textContent;
    } else if (node.nodeType === Node.ELEMENT_NODE) {
      let props = { key: Math.random().toString() }; // Adding a unique key for React elements
      if (node.attributes) {
        for (let attr of node.attributes) {
          if (['href', 'src', 'alt', 'title'].includes(attr.name)) {
            props[attr.name] = sanitizeContent(attr.value);
          }
        }
      }
      const children = Array.from(node.childNodes).map(createElement);
      return React.createElement(node.tagName.toLowerCase(), props, ...children);
    }
  };

  const bodyChildren = Array.from(doc.body.childNodes).map(createElement);
  return bodyChildren.length === 1 ? bodyChildren[0] : bodyChildren;
}