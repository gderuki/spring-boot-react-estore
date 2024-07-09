export const toKebabCase = (str, keepCase = false) => {
  if (!str) {
    return '';
  }
  const kebabCaseStr = str
    .replace(/([a-z0-9])([A-Z])/g, '$1-$2')
    .replace(/\s+/g, '-')
    .replace(/(?<=[0-9])-([A-Z0-9])/gi, '$1');

  return keepCase ? kebabCaseStr : kebabCaseStr.toLowerCase();
};