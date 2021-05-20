module.exports = {
    title: 'JDash',
    tagline: 'A reactive Geometry Dash API wrapper for Java.',
    url: 'https://jdash.alex1304.com',
    baseUrl: '/',
    favicon: 'favicon.ico',
    onBrokenLinks: 'throw',
    onBrokenMarkdownLinks: 'warn',
    organizationName: 'Alex1304', // Usually your GitHub org/user name.
    projectName: 'jdash', // Usually your repo name.
    themeConfig: {
        colorMode: {
            defaultMode: "dark"
        },
        navbar: {
            title: 'JDash',
            items: [
                {
                    href: 'https://github.com/Alex1304/jdash',
                    label: 'GitHub',
                    position: 'right',
                },
            ],
        },
        footer: {
            style: 'dark',
            links: [],
            copyright: `Copyright © ${new Date().getFullYear()} Alexandre Miranda (Alex1304). Built with Docusaurus.`,
        },
        prism: {
            theme: require('prism-react-renderer/themes/vsDark'),
            additionalLanguages: ['java', 'groovy', 'properties']
        },
        googleAnalytics: {
            trackingID: 'G-KZS4FVPEGD'
        },
        gtag: {
            trackingID: 'G-KZS4FVPEGD'
        }
    },
    presets: [
        [
            '@docusaurus/preset-classic',
            {
                docs: {
                    routeBasePath: '/',
                    sidebarPath: require.resolve('./sidebars.js'),
                    // Please change this to your repo.
                    editUrl:
                    'https://github.com/Alex1304/jdash/edit/master/website/',
                },
                /*blog: {
                    showReadingTime: true,
                    // Please change this to your repo.
                    editUrl:
                    'https://github.com/facebook/docusaurus/edit/master/website/blog/',
                },*/
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
            },
        ],
    ],
};
