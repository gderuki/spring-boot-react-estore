const toObject = (attributes) => {
  if (Array.isArray(attributes)) {
    return attributes.reduce((acc, attr) => {
      const [key, value] = Object.entries(attr)[0];
      acc[key] = value;
      return acc;
    }, {});
  } else {
    return attributes;
  }
};

export const generateCompositeKey = (id, attributes) => {
  const attributesString = Object.entries(toObject(attributes))
    .sort((a, b) => a[0].localeCompare(b[0]))
    .map(([key, value]) => `${key}:${value}`)
    .join('|');
  return `${id}|${attributesString}`;
};

export const extractProductIdFromCompositeKey = (compositeKey) => {
  return compositeKey.split('|')[0];
};