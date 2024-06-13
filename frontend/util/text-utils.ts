function shortenText(input: string, maxLength: number, includeDotsInLength: boolean = true) {
    if (input.length > maxLength) {
        const actualMaxLength = includeDotsInLength ? maxLength - 3 : maxLength;
        return `${input.substring(0, actualMaxLength < 0 ? 0 : actualMaxLength)}...`;
    }

    return input;
}

export function truncateText(input: string | undefined, maxLength: number | undefined) {
    input = input ?? "";
    maxLength = maxLength ?? 20;

    return shortenText(input, maxLength);
}