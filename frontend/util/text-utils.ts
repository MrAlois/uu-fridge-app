function shortenText(input: string, maxLength: number) {
    return input.length > maxLength ? `${input.substring(0, maxLength)}...` : input;
}

export function truncateText(input: string | undefined, maxLength: number | undefined) {
    return input == null ? "" : shortenText(input, maxLength ? maxLength : 20);
}